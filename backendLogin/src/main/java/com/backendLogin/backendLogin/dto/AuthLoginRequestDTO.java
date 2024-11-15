package com.backendLogin.backendLogin.dto;  // Declara el paquete en el que se encuentra la clase DTO

import jakarta.validation.constraints.NotBlank;  // Importa la anotación @NotBlank para validación de campos no vacíos

// Define un "record" que es una nueva característica en Java para representar objetos inmutables.
// El record AuthLoginRequestDTO tiene dos campos: username y password, ambos con la anotación @NotBlank.
public record AuthLoginRequestDTO(@NotBlank String username,   // Campo 'username' que no puede estar vacío o nulo
                                  @NotBlank String password) {  // Campo 'password' que también debe ser no vacío o nulo
}

