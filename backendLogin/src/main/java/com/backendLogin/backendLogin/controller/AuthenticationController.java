package com.backendLogin.backendLogin.controller;
                                                               //Esta clase maneja peticiones relacionadas con la autenticación de los usuarios, como inicio de sesion
import org.springframework.beans.factory.annotation.Autowired;        
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.dto.AuthLoginRequestDTO;
import com.backendLogin.backendLogin.dto.AuthResponseDTO;
import com.backendLogin.backendLogin.service.UserDetailsServiceImp;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest) {
        // Llamamos al servicio para realizar el login y obtenemos la respuesta con el JWT, mensaje, estado y ID
        AuthResponseDTO response = this.userDetailsService.loginUser(userRequest);
        
        // Devolvemos la respuesta con código HTTP 200 (OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/login/{id}")
    public ResponseEntity<AuthResponseDTO> login(@PathVariable Long id, @RequestBody @Valid AuthLoginRequestDTO userRequest) {
        // Llamamos al servicio para realizar el login y obtener la respuesta con el JWT y el mensaje
        AuthResponseDTO response = this.userDetailsService.loginUser(userRequest);

        // Aquí podrías usar el ID capturado de la URL si es necesario para la lógica
        System.out.println("Login solicitado para el ID de usuario: " + id);
        
        // Devolvemos la respuesta con código HTTP 200 (OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}



/*Resumen de la lógica: El cliente envía una solicitud POST a /auth/login con un cuerpo que contiene las credenciales del usuario (por ejemplo, un nombre de usuario y contraseña).
El controlador AuthenticationController recibe esta solicitud y pasa las credenciales al servicio UserDetailsServiceImp a través del método loginUser.
El servicio loginUser valida las credenciales del usuario:
Si son válidas, genera un token JWT o una respuesta de éxito.
Si no son válidas, devuelve un error (aunque no está manejado explícitamente aquí).
El controlador devuelve la respuesta con un HTTP 200 OK si la autenticación fue exitosa y con el cuerpo de la respuesta adecuado (probablemente un token de autenticación o información sobre el usuario).
Este flujo de trabajo es común en aplicaciones que manejan autenticación basada en tokens o JWT para permitir el acceso seguro a rutas protegidas.*/
	
