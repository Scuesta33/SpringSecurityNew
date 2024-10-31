package com.backendLogin.backendLogin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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