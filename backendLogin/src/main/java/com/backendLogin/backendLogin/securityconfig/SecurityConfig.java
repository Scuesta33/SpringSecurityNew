package com.backendLogin.backendLogin.securityconfig;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura la política de sesión como sin estado
	        )
	      
	        .httpBasic(Customizer.withDefaults()); // Habilita la autenticación básica

	    return http.build(); // Devuelve la configuración construida
	}



    
	// Configura el AuthenticationManager para manejar las autenticaciones
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Devuelve el AuthenticationManager configurado
    }

    // Crea y configura un AuthenticationProvider para la autenticación de usuarios
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Crea un nuevo DaoAuthenticationProvider
        provider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas
        provider.setUserDetailsService(userDetailsService()); // Establece el servicio de detalles del usuario
        return provider; // Devuelve el provider configurado
    }

    // Define un PasswordEncoder que no realiza ninguna codificación (para fines de prueba)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Utiliza NoOpPasswordEncoder para no codificar contraseñas
    }

    // Crea y configura un UserDetailsService con usuarios en memoria
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>(); // Lista para almacenar detalles de usuarios en memoria
        
        // Añade un usuario "sergi" con el rol de "ADMIN" y varias autoridades
        userDetailsList.add(User.withUsername("sergi")
                .password("1234") // Contraseña del usuario
                .roles("ADMIN") // Rol del usuario
                .authorities("CREATE", "READ", "UPDATE", "DELETE") // Autoridades específicas del usuario
                .build());

        // Añade un usuario "angela" con el rol de "USER" y una autoridad
        userDetailsList.add(User.withUsername("angela")
                .password("1234") // Contraseña del usuario
                .roles("USER") // Rol del usuario
                .authorities("READ") // Autoridades específicas del usuario
                .build());
        
        userDetailsList.add(User.withUsername("pepe")
                .password("1234") // Contraseña del usuario
                .roles("USER") // Rol del usuario
                .authorities("READ") // Autoridades específicas del usuario
                .build());
        
        return new InMemoryUserDetailsManager(userDetailsList); // Devuelve un gestor de usuarios en memoria con los usuarios configurados
    }
}
    	
    