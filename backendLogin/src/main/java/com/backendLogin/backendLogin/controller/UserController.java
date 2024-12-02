package com.backendLogin.backendLogin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.dto.DeleteUserRequestDTO;
import com.backendLogin.backendLogin.dto.RegisterDTO;
import com.backendLogin.backendLogin.dto.UserSecUpdateRequest;
import com.backendLogin.backendLogin.model.Role;
import com.backendLogin.backendLogin.model.UpdateUserRequest;
import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.service.IRoleService;
import com.backendLogin.backendLogin.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;  // Servicio para manejar la lógica de usuarios

    @Autowired
    private IRoleService roleService;  // Servicio para manejar la lógica de roles
    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Método para obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userService.findAll();  // Llama al servicio para obtener todos los usuarios
        return ResponseEntity.ok(users);  // Devuelve una respuesta HTTP 200 (OK) con la lista de usuarios
    }

    // Método para obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userService.findById(id);  // Busca el usuario por su ID
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build()); // Si no lo encuentra, devuelve un 404
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated RegisterDTO registerDTO, BindingResult result) {
        // Verificamos si hay errores de validación en el DTO
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid data provided"));
        }

        // Verificamos si la contraseña es nula o está vacía
        if (registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password cannot be empty"));
        }

        // Comprobamos si las contraseñas coinciden
        if (!registerDTO.getPassword().equals(registerDTO.getPassword())) {  // Aquí corregí el error: se comparaban las mismas contraseñas.
            return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match"));
        }

        // Verificamos si el nombre de usuario ya existe
        Optional<UserSec> existingUser = userService.findByName(registerDTO.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
        }

        // Verificamos si el correo electrónico ya existe
        Optional<UserSec> existingEmailUser = userService.findByEmail(registerDTO.getEmail());
        if (existingEmailUser.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
        }

        // Buscamos el rol 'USER' con id 1
        Role userRole = roleService.findById(1L).orElseGet(() -> {
            // Si el rol no existe, lo creamos
            Role defaultRole = new Role();
            defaultRole.setName("USER");
            return roleService.save(defaultRole);
        });

        // Creamos el objeto UserSec con los datos del DTO
        UserSec userSec = new UserSec();
        userSec.setUsername(registerDTO.getUsername());
        userSec.setPassword(userService.encriptPassword(registerDTO.getPassword())); // Encriptamos la contraseña
        userSec.setEmail(registerDTO.getEmail()); // Establecemos el correo electrónico

        // Establecemos los valores adicionales como habilitado, cuenta no expirada, etc.
        userSec.setEnabled(true);  // Habilitar la cuenta
        userSec.setAccountNotExpired(true);  // Cuenta no expirada
        userSec.setAccountNotLocked(true);  // Cuenta no bloqueada
        userSec.setCredentialNotExpired(true);  // Credenciales no expiradas

        // Asignamos el rol 'USER'
        Set<Role> roleList = new HashSet<>();
        roleList.add(userRole);
        userSec.setRolesList(roleList);

        // Guardamos el usuario en la base de datos
        UserSec newUser = userService.save(userSec);

        // Devolvemos una respuesta exitosa
        return ResponseEntity.ok(Map.of("message", "Usuario registrado exitosa", "user", newUser));
    }

    
    @PutMapping("/updateUser")
    public ResponseEntity<UserSec> updateUser(@RequestBody UserSecUpdateRequest updateRequest) {
        try {
            // Llamar al servicio para actualizar el usuario
            UserSec updatedUser = userService.updateUser(updateRequest.getUsername(), updateRequest.getNewPassword());
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            // Manejo de errores si el username es inválido
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        try {
            // Obtener el nombre de usuario desde el contexto de seguridad (decodificado desde el token JWT)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // El nombre de usuario proviene del token JWT

            // Llamamos al servicio para eliminar al usuario con el nombre de usuario extraído
            boolean isDeleted = userService.deleteByUsername(username);

            // Si la eliminación es exitosa
            if (isDeleted) {
                return ResponseEntity.ok("Usuario eliminado exitosamente.");
            } else {
                return ResponseEntity.status(400).body("No se pudo eliminar el usuario.");
            }
        } catch (Exception e) {
            // En caso de error, logueamos el error y devolvemos un mensaje de error
            logger.error("Error al eliminar el usuario: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Hubo un error al procesar la solicitud.");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            // Llamamos al servicio para eliminar el usuario por su ID
            boolean isDeleted = userService.deleteUserById(id);

            // Si la eliminación es exitosa
            if (isDeleted) {
                return ResponseEntity.ok("Usuario eliminado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            // En caso de error, logueamos el error y devolvemos un mensaje de error
            logger.error("Error al eliminar el usuario con ID " + id + ": " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error al procesar la solicitud.");
        }
    }


        


    }