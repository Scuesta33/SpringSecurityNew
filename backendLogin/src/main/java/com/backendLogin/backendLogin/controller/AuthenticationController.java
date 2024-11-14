package com.backendLogin.backendLogin.controller;
                                                               //Esta clase maneja peticiones relacionadas con la autenticación de los usuarios, como inicio de sesion
import org.springframework.beans.factory.annotation.Autowired;        
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.dto.AuthLoginRequestDTO;
import com.backendLogin.backendLogin.dto.AuthResponseDTO;
import com.backendLogin.backendLogin.service.UserDetailsServiceImp;

import jakarta.validation.Valid;

@RestController //controlador Rest
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserDetailsServiceImp userDetailsService; //inyectamos la dependencia de UserDetaisServiceImp 
//Método para inicio de sesión	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login (@RequestBody @Valid AuthLoginRequestDTO userRequest) { //RequestBody indica que los datos enviados por el cliente en el cuerpo de la solicitud serán convertidos a un objeto de tipo AuthLoginRequestDTO
	    return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK); //Valid asegura que el objeto userRequest se validará según las reglas de DTO
	}//se llama al método loginUser del servicio userDetailsService, pasándole el objeto userRequest. Este servicio se encarga de procesar la solicitud de inicio de sesión y devolver una respuesta.
	//loginUser verificará las credenciales(nombre de usuario y contraseña), autentificará al usuario y, si es exitoso, generará un token JWT o una sesión para el usuario.
}   /*Finalmente, el método devuelve un ResponseEntity, que encapsula la respuesta HTTP. El cuerpo de la respuesta será el resultado de userDetailsService.loginUser(userRequest) (probablemente un token de autenticación o información relevante sobre la autenticación).
HttpStatus.OK indica que la solicitud fue exitosa (código de estado HTTP 200)*/


/*Resumen de la lógica: El cliente envía una solicitud POST a /auth/login con un cuerpo que contiene las credenciales del usuario (por ejemplo, un nombre de usuario y contraseña).
El controlador AuthenticationController recibe esta solicitud y pasa las credenciales al servicio UserDetailsServiceImp a través del método loginUser.
El servicio loginUser valida las credenciales del usuario:
Si son válidas, genera un token JWT o una respuesta de éxito.
Si no son válidas, devuelve un error (aunque no está manejado explícitamente aquí).
El controlador devuelve la respuesta con un HTTP 200 OK si la autenticación fue exitosa y con el cuerpo de la respuesta adecuado (probablemente un token de autenticación o información sobre el usuario).
Este flujo de trabajo es común en aplicaciones que manejan autenticación basada en tokens o JWT para permitir el acceso seguro a rutas protegidas.*/
	
