package com.backendLogin.backendLogin.securityconfig.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.backendLogin.backendLogin.utils.JwtUtils;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//La clase JwtTokenValidator extiende de OncePerRequestFilter, lo que significa que este filtro
//se ejecutará una vez por cada solicitud HTTP entrante.
public class JwtTokenValidator extends OncePerRequestFilter {

 // Instancia de JwtUtils para utilizar métodos como la validación de token y extracción de información.
 private JwtUtils jwtUtils;

 // Constructor que recibe un objeto JwtUtils y lo asigna a la variable jwtUtils.
 public JwtTokenValidator(JwtUtils jwtUtils) {
     this.jwtUtils = jwtUtils;
 }

 // Método principal que se ejecuta para cada solicitud HTTP. Realiza el proceso de validación del JWT.
 @Override
 protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

     // Obtiene el token JWT del encabezado Authorization de la solicitud HTTP.
     String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

     // Verifica si el token está presente.
     if (jwtToken != null) {
         // El token tiene el formato "Bearer <token>", así que eliminamos "Bearer " (7 caracteres).
         jwtToken = jwtToken.substring(7);

         // Valida el token y decodifica la información contenida en él usando JwtUtils.
         DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

         // Extrae el nombre de usuario del token decodificado.
         String username = jwtUtils.extractUsername(decodedJWT);

         // Extrae las autoridades (roles/privilegios) del token.
         String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

         // Convierte el String de autoridades a una lista de GrantedAuthority.
         Collection<? extends GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

         // Obtiene el SecurityContext actual donde se mantiene el contexto de seguridad de la aplicación.
         SecurityContext context = SecurityContextHolder.getContext();

         // Crea un nuevo objeto de autenticación con el nombre de usuario y las autoridades extraídas.
         Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);

         // Establece el objeto de autenticación en el contexto de seguridad.
         context.setAuthentication(authentication);

         // Asocia el contexto de seguridad actual con la solicitud.
         SecurityContextHolder.setContext(context);
     }

     // Continúa con el siguiente filtro de la cadena de filtros (si lo hay).
     filterChain.doFilter(request, response);
 }
}


