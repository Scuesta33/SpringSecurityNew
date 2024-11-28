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

@Component // Anotación que indica que esta clase es un componente de Spring y será gestionada como un bean dentro del contexto de la aplicación.
public class JwtUtils {
  
  // Se obtienen los valores de la configuración desde el archivo de propiedades (por ejemplo, `application.properties` o `application.yml`)
  @Value("${security.jwt.private.key}")
  private String privateKey; // La clave privada utilizada para firmar el JWT.
  
  @Value("${security.jwt.user.generator}")
  private String userGenerator; // El nombre del generador del token (generalmente la aplicación o servidor que genera el token).
  
  // Método para crear un JWT a partir de la autenticación del usuario
  public String createToken(Authentication authentication, Long userId) {
	    // El algoritmo HMAC256 usa una clave secreta para firmar el JWT
	    Algorithm algorithm = Algorithm.HMAC256(privateKey);

	    // Se obtiene el nombre de usuario del principal (usuario autenticado)
	    String username = authentication.getPrincipal().toString();

	    // Se obtiene una lista de las autoridades (roles y permisos) del usuario y se convierte a una cadena separada por comas
	    String authorities = authentication.getAuthorities()
	        .stream()
	        .map(GrantedAuthority::getAuthority)  // Extrae la autoridad de cada `GrantedAuthority`
	        .collect(Collectors.joining(","));  // Las autoridades se concatenan en una cadena separada por comas

	    // Creación del JWT con las distintas propiedades
	     // Devuelve el JWT generado
	 // Creación del JWT con las distintas propiedades
	    String jwtToken = JWT.create()  // Usamos la librería JWT para construir el token
	        .withIssuer(this.userGenerator)  // Establece el generador del token
	        .withSubject(username)  // Establece el "subject" del token (usualmente el username del usuario)
	        .withClaim("authorities", authorities)  // Agrega el claim "authorities" con los roles/permisos del usuario
	        .withClaim("id", userId)  // Aquí cambiamos "userId" a "id"
	        .withIssuedAt(new Date())  // Fecha de emisión del token
	        .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))  // El token expira en 30 minutos (1800000 milisegundos)
	        .withJWTId(UUID.randomUUID().toString())  // ID único del token
	        .withNotBefore(new Date(System.currentTimeMillis()))  // El token no será válido antes de la fecha actual
	        .sign(algorithm);  // Firma el token con el algoritmo especificado (HMAC256)

	    return jwtToken;  // Devuelve el JWT generado

	}

  
  // Método para validar y decodificar un JWT
  public DecodedJWT validateToken(String token) {
    try {
      // Creamos el algoritmo para verificar la firma del JWT con la misma clave privada
      Algorithm algorithm = Algorithm.HMAC256(privateKey);
      // Configuramos el verificador de JWT con el mismo issuer que utilizamos al crear el token
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer(this.userGenerator)  // Verificamos que el issuer sea el mismo
          .build();
      
      // Si el token es válido, lo decodificamos y devolvemos los datos del token
      DecodedJWT decodedJWT = verifier.verify(token);
      return decodedJWT;  // Devuelve el JWT decodificado
    } catch (JWTVerificationException exception) {
      // Si el token no es válido, lanzamos una excepción
      throw new JWTVerificationException("Invalid token. Not authorized");
    }
  }

  // Método para extraer el nombre de usuario del JWT decodificado
  public String extractUsername(DecodedJWT decodedJWT) {
    return decodedJWT.getSubject().toString();  // El "subject" del JWT es el nombre de usuario
  }
  
  // Método para obtener un claim específico del JWT decodificado
  public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
    return decodedJWT.getClaim(claimName);  // Devuelve un claim específico (por ejemplo, "authorities")
  }

  // Método para obtener todos los claims del JWT decodificado
  public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
    return decodedJWT.getClaims();  // Devuelve todos los claims (como un mapa clave-valor)
  }

  // Método para extraer el 'id' del JWT decodificado
  public Long extractUserId(DecodedJWT decodedJWT) {
    return decodedJWT.getClaim("id").asLong();  // Extraemos el 'id' como Long desde el JWT
  }
}
