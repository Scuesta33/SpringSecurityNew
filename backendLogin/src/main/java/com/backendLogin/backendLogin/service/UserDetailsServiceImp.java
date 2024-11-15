package com.backendLogin.backendLogin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication; // correcto

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.dto.AuthLoginRequestDTO;
import com.backendLogin.backendLogin.dto.AuthResponseDTO;
import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;
import com.backendLogin.backendLogin.utils.JwtUtils;

@Service // Indica que esta clase es un servicio de Spring, un bean gestionado por el contenedor de Spring.
public class UserDetailsServiceImp implements UserDetailsService {  // Implementa la interfaz UserDetailsService para cargar detalles del usuario.

	@Autowired
	private IUserRepository userRepo;  // Inyecta el repositorio de usuarios, que se utiliza para acceder a los datos de los usuarios en la base de datos.
	
	@Autowired
	private JwtUtils jwtUtils;  // Inyecta una clase utilitaria para manejar la creación y validación de tokens JWT.
	
	@Autowired
	private PasswordEncoder passwordEncoder;  // Inyecta un codificador de contraseñas para validar las contraseñas.

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  // Método que se llama para cargar un usuario basado en el nombre de usuario.

		// Buscar el usuario en la base de datos por nombre de usuario.
		UserSec userSec = userRepo.findUserEntityByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));
		
		// Crear una lista de autoridades (permisos y roles).
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		// Para cada rol del usuario, convertirlo a una autoridad de Spring Security (SimpleGrantedAuthority) con el prefijo "ROLE_".
		userSec.getRolesList()
		       .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

		// Traer permisos desde los roles y convertirlos en SimpleGrantedAuthority.
		// Un "flatMap" se usa para aplanar los roles y los permisos en una única lista.
		userSec.getRolesList().stream()
		       .flatMap(role -> role.getPermissionsList().stream())  // Flattens the permissions of each role.
		       .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));  // Agregar permiso como autoridad.

		// Crear un objeto User (de Spring Security) a partir del usuario y sus autoridades (roles y permisos).
		return new User(
				userSec.getUsername(),  // Nombre de usuario.
				userSec.getPassword(),  // Contraseña.
				userSec.isEnabled(),  // Si el usuario está habilitado.
		        userSec.isAccountNotExpired(),  // Si la cuenta no ha expirado.
				userSec.isCredentialNotExpired(),  // Si las credenciales no han expirado.
				userSec.isAccountNotLocked(),  // Si la cuenta no está bloqueada.
				authorityList  // Lista de autoridades (roles y permisos).
			);
	}

	// Método para realizar el login de un usuario.
	public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
		
		// Recuperar el nombre de usuario y la contraseña del objeto DTO.
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();
		
		// Intentar autenticar al usuario con el nombre de usuario y la contraseña.
		Authentication authentication = this.authenticate(username, password);
		
		// Establecer el contexto de seguridad con la autenticación exitosa.
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// Crear un token de acceso JWT basado en la autenticación.
		String accessToken = jwtUtils.createToken(authentication);
		
		// Crear una respuesta DTO con el JWT y un mensaje de éxito.
		AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Login successful", accessToken, true);
		
		// Devolver el objeto AuthResponseDTO.
		return authResponseDTO;
	}
	
	// Método para autenticar un usuario dado su nombre de usuario y contraseña.
	public Authentication authenticate(String username, String password) {
		
		// Cargar los detalles del usuario desde la base de datos.
		UserDetails userDetails = this.loadUserByUsername(username);
		
		// Si no se encuentra el usuario, lanzar una excepción de credenciales incorrectas.
		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username or password");
		}
		
		// Si la contraseña no coincide con la almacenada, lanzar una excepción de credenciales incorrectas.
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		
		// Si la autenticación es exitosa, devolver un objeto de autenticación con el nombre de usuario y las autoridades (roles y permisos).
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
	}
}

