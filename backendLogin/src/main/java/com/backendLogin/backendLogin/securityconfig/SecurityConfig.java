package com.backendLogin.backendLogin.securityconfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.backendLogin.backendLogin.utils.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
	@Autowired
	private JwtUtils jwtUtils;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura la política de sesión como sin estado
	        )
	      
	        .httpBasic(Customizer.withDefaults()); // Habilita la autenticación básica
	        .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class);

	    return http.build(); // Devuelve la configuración construida
	}



    
	// Configura el AuthenticationManager para manejar las autenticaciones
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Devuelve el AuthenticationManager configurado
    }

    // Crea y configura un AuthenticationProvider para la autenticación de usuarios
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Crea un nuevo DaoAuthenticationProvider
        provider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas
        provider.setUserDetailsService(userDetailsService); // Establece el servicio de detalles del usuario
        return provider; // Devuelve el provider configurado
    }

    // Define un PasswordEncoder que no realiza ninguna codificación (para fines de prueba)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }

   
}
    	
    