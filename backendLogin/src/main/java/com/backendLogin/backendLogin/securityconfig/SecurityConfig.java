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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.backendLogin.backendLogin.securityconfig.filter.JwtTokenValidator;
import com.backendLogin.backendLogin.utils.JwtUtils;

//Marca esta clase como una clase de configuración de Spring que se encargará de la configuración de seguridad en la aplicación.
@Configuration
//Activa la seguridad web en la aplicación, habilitando las protecciones por defecto como el filtrado de solicitudes HTTP.
@EnableWebSecurity
//Habilita la seguridad en métodos de controladores (como la autorización de roles).
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer{
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")  // Configura las rutas para las que habilitar CORS
	                .allowedOrigins("http://localhost:4200")  // Permite a tu frontend (cambia según tu caso)
	                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos HTTP permitidos
	                .allowedHeaders("*")  // Permite cualquier tipo de cabeceras
	                .allowCredentials(true);  // Permite enviar credenciales (como cookies o cabeceras Authorization)
	    }


 // Inyecta una instancia de la clase JwtUtils para gestionar la validación del token JWT.
 @Autowired
 private JwtUtils jwtUtils;

 // Configura los filtros de seguridad y el manejo de las solicitudes HTTP.
 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     // Desactiva la protección CSRF (Cross-Site Request Forgery), ya que no es necesaria para una API RESTful que usa tokens JWT.
     http
         .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF
         // Configura la política de sesiones a "stateless", lo que significa que la aplicación no mantiene estado entre las solicitudes.
         .sessionManagement(session -> 
             session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Establece la política de sesiones como stateless
         )
         // Habilita la autenticación básica HTTP para los endpoints que lo requieran (aunque en este caso no es necesario por JWT).
         .httpBasic(Customizer.withDefaults()) 
         // Añade un filtro para validar el token JWT antes de procesar cualquier otra autenticación.
         .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class); // Añade el filtro JWT

     // Devuelve el filtro de seguridad configurado.
     return http.build(); 
 }

 // Crea y configura un AuthenticationManager para manejar las autenticaciones de usuarios.
 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
     // Obtiene el AuthenticationManager de la configuración de autenticación predeterminada.
     return authenticationConfiguration.getAuthenticationManager(); // Devuelve el AuthenticationManager configurado
 }

 // Crea y configura un AuthenticationProvider para autenticar a los usuarios, usando un UserDetailsService.
 @Bean
 public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
     DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Crea una instancia del proveedor de autenticación DaoAuthenticationProvider
     provider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas, que usará BCrypt
     provider.setUserDetailsService(userDetailsService); // Asocia el UserDetailsService que carga la información del usuario
     return provider; // Devuelve el AuthenticationProvider configurado
 }

 // Define un PasswordEncoder que usará BCrypt para codificar las contraseñas.
 @Bean
 public PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder(); // Devuelve un PasswordEncoder configurado con BCrypt.
 }
}

    	
    