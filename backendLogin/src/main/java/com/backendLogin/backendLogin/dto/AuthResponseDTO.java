package com.backendLogin.backendLogin.dto;  // Define el paquete en el que se encuentra este DTO

import com.fasterxml.jackson.annotation.JsonPropertyOrder;  // Importa la anotación JsonPropertyOrder para controlar el orden de las propiedades en el JSON resultante

// Define un "record" llamado AuthResponseDTO. Este tipo de clase es inmutable y compacta en Java.
@JsonPropertyOrder({"username", "message", "jwt", "status", "id"})  // Anotación que define el orden de los campos cuando se convierte en JSON
public record AuthResponseDTO (String username,     // Campo para el nombre de usuario
                               String message,      // Campo para el mensaje de respuesta, por ejemplo, "Login successful"
                               String jwt,          // Campo para el JWT (token de autenticación)
                               Boolean status,
                               Long id) {    // Campo para el estado de la autenticación (true si es exitosa, false si falla)
}
