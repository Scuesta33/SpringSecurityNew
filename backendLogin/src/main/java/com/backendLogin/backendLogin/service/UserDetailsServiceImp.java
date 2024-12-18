
package com.backendLogin.backendLogin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;

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

    public UserSec registerOAuthUser(String username, String email, String provider) {
        // Verificar si el usuario ya existe
        UserSec existingUser = userRepo.findByEmail(email).orElse(null);

        if (existingUser == null) {
            // Si el usuario no existe, se crea un nuevo usuario
            UserSec newUser = new UserSec();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setProvider(provider);
            newUser.setEnabled(true);
            newUser.setPassword(passwordEncoder.encode("tempPassword"));  // Contraseña temporal

            // Guardar el nuevo usuario
            userRepo.save(newUser);

            return newUser;
        } else {
            // Si ya existe, simplemente retornamos el usuario
            return existingUser;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSec userSec = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        if (userSec.getRolesList() != null && !userSec.getRolesList().isEmpty()) {
            userSec.getRolesList().forEach(role -> {
                if (role.getName() != null && !role.getName().isEmpty()) {
                    authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName())));
                }
            });
        }

        userSec.getRolesList().stream()
               .flatMap(role -> role.getPermissionsList().stream())
               .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));

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

    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserSec userSec = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));

        Long userId = userSec.getId();
        String accessToken = jwtUtils.createToken(authentication, userId);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(
                username, 
                "Login successful", 
                accessToken, 
                true, 
                userSec.getId()
        );

        return authResponseDTO;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
