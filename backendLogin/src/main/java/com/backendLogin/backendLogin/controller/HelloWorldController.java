package com.backendLogin.backendLogin.controller;

import org.springframework.security.access.prepost.PreAuthorize;    /* La anotación @PreAuthorize permite configurar el acceso a los métodos a nivel de seguridad en función de expresiones SpEL (Spring Expression Language). Estas expresiones se evalúan para decidir si un usuario tiene permiso para ejecutar el método. Algunos ejemplos comunes son:

                                                                                hasRole('ROLE_ADMIN'): El usuario debe tener el rol ROLE_ADMIN.
                                                                                hasAuthority('READ'): El usuario debe tener la autoridad READ.
                                                                                permitAll(): El acceso está permitido para todos, sin ninguna restricción.
                                                                                denyAll(): El acceso está denegado para todos, lo que se establece globalmente en la clase, como se hizo aquí con la anotación en la clase.*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("denyAll()")
public class HelloWorldController {
	
	@GetMapping("/holaseg")
	@PreAuthorize("hasAuthority('READ')")
	public String secHelloWorld() {
		return "Hola Mundo con seguridad";
	}
	
	@GetMapping("/holanoseg")
	@PreAuthorize("permitAll()")
	public String noSecHelloWorld() {
		return "Hola mundo sin seguridad";
	}
	
}