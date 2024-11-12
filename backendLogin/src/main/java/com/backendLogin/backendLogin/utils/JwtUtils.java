package com.backendLogin.backendLogin.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {
  // Aquí va código Jwt
	@Value("${security.jwt.private.key}")
	private String privateKey;
	@Value("${security.jwt.user.generator}")
	private String userGenerator;
	
	//creación de tokens
	public String createToken(Authentication authentication) {
		
		Algorithm algorithm = Algorithm.HMAC256(privateKey);
        
		//queda en el contexto holder
        String username = authentication.getPrincipal().toString();
        
        String authorities = authentication.getAuthorities()
        		.stream()
        		.map(GrantedAuthority::getAuthority)
        		.collect(Collectors.joining(","));
        
        String jwtToken = JWT.create()
        		.withIssuer(this.userGenerator)
        		.withSubject(username)
        		.withClaim("authorities", authorities)
        		.withIssuedAt(new Date())
        		.withExpiresAt(new Date(System.currentTimeMillis() +  1800000))
        		.withJWTId(UUID.randomUUID().toString())
        		.withNotBefore(new Date(System.currentTimeMillis()))
        		.sign(algorithm);
        return jwtToken;
		}
	
	//decodificar y validar nuestros tokens
	public DecodedJWT validateToken (String token) {

		try {
			Algorithm algorithm = Algorithm.HMAC256(privateKey);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(this.userGenerator)
					.build();
			
			DecodedJWT decodedJWT = verifier.verify(token);
			return decodedJWT;
		}
		catch (JWTVerificationException exception) {
			throw new JWTVerificationException("Invalid toekn. Not authorized");
		}
	}
  
	//método para obtener el usuario/username
	public String extractUsername(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject().toString();
	}
	
	//obtener en claim en particular
	public Claim getSpecificClaim (DecodedJWT decodedJWT, String claimName) {
		return decodedJWT.getClaim(claimName);
	}
	
	//obtener los claims 
	
	public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT){
		return decodedJWT.getClaims();
	}
}
