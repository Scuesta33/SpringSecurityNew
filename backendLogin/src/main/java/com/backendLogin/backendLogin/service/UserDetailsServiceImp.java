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

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private IUserRepository userRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//tenemos nuestro UserSec y necesitamos devolverlo en formato UserDetails
		//traer nuestro usuario de la base de datos
		UserSec userSec = userRepo.findUserEntityByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));
		//creamos una lista para los Permisos
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		/// traer roles y convertirlos en SimpleGrantedAuthority
		
		userSec.getRolesList()
		       .forEach(role -> authorityList.add (new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));
		
		
		
		
		//traer permisos y convertirlos en SimpleGrantedAuthority
		userSec.getRolesList().stream()
		       .flatMap(role -> role.getPermissionsList().stream())
		       .forEach(permission -> authorityList.add (new SimpleGrantedAuthority(permission.getPermissionName())));
		
		return new User(
				userSec.getUsername(),
				userSec.getPassword(),
				userSec.isEnabled(),
		        userSec.isAccountNotExpired(),
				userSec.isCredentialNotExpired(),
				userSec.isAccountNotLocked(),
				authorityList
			);
		
	}
    
	public AuthResponseDTO loginUser (AuthLoginRequestDTO authLoginRequest) {
		
		//recuperamos nombre de usuario y contraseña
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();
		
		Authentication authentication = this.authenticate(username, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.createToken(authentication);
		AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Login successfull", accessToken, true);
				return authResponseDTO;
	}
	
	public Authentication authenticate (String username, String password) {
		
		UserDetails userDetails = this.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())){
			throw new BadCredentialsException("Invalid username or password");
		}
		
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
	}
}
