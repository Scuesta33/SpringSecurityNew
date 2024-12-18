package com.backendLogin.backendLogin.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.private.key}")
    private String privateKey; // Clave privada para firmar el JWT

    @Value("${security.jwt.user.generator}")
    private String userGenerator; // Nombre del generador del token

    // Método para crear un token JWT a partir de la autenticación del usuario
    public String createToken(Authentication authentication, Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey); // Algoritmo para firmar el JWT

        String username = authentication.getPrincipal().toString(); // Obtener el nombre de usuario
        String authorities = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)  // Extraer las autoridades
            .collect(Collectors.joining(","));  // Concatenarlas en una cadena separada por comas

        // Crear el token JWT con las propiedades necesarias
        return JWT.create()
            .withIssuer(userGenerator)  // Establecer el emisor del token
            .withSubject(username)  // Establecer el "subject" (nombre de usuario)
            .withClaim("authorities", authorities)  // Añadir los roles/permisos del usuario
            .withClaim("id", userId)  // Añadir el ID del usuario
            .withIssuedAt(new Date())  // Fecha de emisión
            .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))  // Expira en 30 minutos
            .withJWTId(UUID.randomUUID().toString())  // ID único del token
            .sign(algorithm);  // Firmar el token con el algoritmo especificado
    }

    // Método para validar y decodificar un JWT
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey); // Algoritmo para verificar la firma del token
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(userGenerator)  // Verificar el emisor
                .build();

            return verifier.verify(token);  // Decodificar y verificar el token
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid token. Not authorized"); // Lanzar excepción si el token no es válido
        }
    }

    // Método para extraer el nombre de usuario desde el JWT decodificado
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject(); // Extraer el nombre de usuario del "subject"
    }

    // Método para obtener un claim específico del JWT decodificado
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);  // Obtener un claim específico (por ejemplo, "authorities")
    }

    // Método para obtener todos los claims del JWT
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();  // Obtener todos los claims del token
    }

    // Método para extraer el 'id' del JWT
    public Long extractUserId(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("id").asLong();  // Extraer el ID del usuario desde el claim "id"
    }
}
