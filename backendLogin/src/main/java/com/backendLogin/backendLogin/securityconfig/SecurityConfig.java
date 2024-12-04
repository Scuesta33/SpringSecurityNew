package com.backendLogin.backendLogin.securityconfig;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.backendLogin.backendLogin.securityconfig.filter.JwtTokenValidator;
import com.backendLogin.backendLogin.utils.JwtUtils;

// Clase de configuración de seguridad
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JwtUtils jwtUtils; // Inyecta el JwtUtils para la validación de JWT

    // Configuración de CORS para permitir solicitudes desde el frontend
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Habilita CORS para todas las rutas
                .allowedOrigins("http://localhost:4200", "http://localhost:8080") // Permite solicitudes desde estos orígenes (ajustar según sea necesario)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*") // Permite todas las cabeceras
                .allowCredentials(true); // Permite enviar credenciales (cookies, headers de autorización)
    }

    // Configuración de los filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva CSRF (usado en APIs sin estado)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura las sesiones como sin estado
            )
            .httpBasic(Customizer.withDefaults()) // Permite autenticación básica (si es necesario)
            .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class); // Añade el filtro para validación JWT

        return http.build(); // Devuelve el filtro de seguridad configurado
    }

    // Configuración del AuthenticationManager para la autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Devuelve el AuthenticationManager
    }

    // Configura el AuthenticationProvider para autenticar a los usuarios
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder()); // Establece BCrypt como el codificador de contraseñas
        provider.setUserDetailsService(userDetailsService); // Asocia el servicio de detalles de usuario
        return provider; // Devuelve el provider de autenticación
    }

    // Define el PasswordEncoder para encriptar las contraseñas usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usa BCrypt para la encriptación de contraseñas
    }
}
