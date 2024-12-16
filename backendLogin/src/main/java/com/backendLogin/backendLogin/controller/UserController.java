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
import com.backendLogin.backendLogin.service.EmailService;
import com.backendLogin.backendLogin.service.IRoleService;
import com.backendLogin.backendLogin.service.IUserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;  // Servicio para manejar la lógica de usuarios

    @Autowired
    private IRoleService roleService;  // Servicio para manejar la lógica de roles
    
    @Autowired
    private EmailService emailService;  // Servicio para enviar correos electrónicos
    
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
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid data provided"));
    }

    if (registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
        return ResponseEntity.badRequest().body(Map.of("message", "Password cannot be empty"));
    }

    if (!registerDTO.getPassword().equals(registerDTO.getPassword())) {
        return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match"));
    }

    Optional<UserSec> existingUser = userService.findByName(registerDTO.getUsername());
    if (existingUser.isPresent()) {
        return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
    }

    Optional<UserSec> existingEmailUser = userService.findByEmail(registerDTO.getEmail());
    if (existingEmailUser.isPresent()) {
        return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
    }

    Role userRole = roleService.findById(1L).orElseGet(() -> {
        Role defaultRole = new Role();
        defaultRole.setName("USER");
        return roleService.save(defaultRole);
    });

    UserSec userSec = new UserSec();
    userSec.setUsername(registerDTO.getUsername());
    userSec.setPassword(userService.encriptPassword(registerDTO.getPassword()));
    userSec.setEmail(registerDTO.getEmail());
    userSec.setEnabled(true);
    userSec.setAccountNotExpired(true);
    userSec.setAccountNotLocked(true);
    userSec.setCredentialNotExpired(true);

    Set<Role> roleList = new HashSet<>();
    roleList.add(userRole);
    userSec.setRolesList(roleList);

    UserSec newUser = userService.save(userSec);

    try {
        emailService.sendRegistrationEmail(registerDTO.getEmail());
    } catch (MessagingException e) {
        logger.error("Error sending registration email: " + e.getMessage(), e);
    }

    return ResponseEntity.ok(Map.of("message", "Usuario registrado exitosamente", "user", newUser));
}


    
@PutMapping("/updateUser")
public ResponseEntity<UserSec> updateUser(@RequestBody UserSecUpdateRequest updateRequest) {
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Llamada al servicio para obtener el usuario actualizado
        UserSec updatedUser = userService.updateUser(
            username,
            updateRequest.getNewUsername(),
            updateRequest.getNewPassword(),
            updateRequest.getNewEmail()
        );

        // Comprobar si hubo cambios en alguno de los campos importantes
        boolean isEmailUpdated = !updatedUser.getEmail().equals(updateRequest.getNewEmail());
        boolean isUsernameUpdated = !updatedUser.getUsername().equals(updateRequest.getNewUsername());
        boolean isPasswordUpdated = !updatedUser.getPassword().equals(updateRequest.getNewPassword());

        // Si alguno de los campos ha sido actualizado, enviamos el correo
        if (isEmailUpdated || isUsernameUpdated || isPasswordUpdated) {
            emailService.sendCredentialUpdateEmail(updatedUser.getEmail());
        }

        return ResponseEntity.ok(updatedUser);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
@DeleteMapping("/delete")
public ResponseEntity<String> deleteUser() {
    try {
        // Obtener el nombre de usuario del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar al usuario antes de eliminarlo
        Optional<UserSec> user = userService.findByName(username);
        if (!user.isPresent()) {
            return ResponseEntity.status(400).body("Usuario no encontrado.");
        }

        // Enviar el correo de eliminación antes de eliminar al usuario
        emailService.sendAccountDeletionEmail(user.get().getEmail());

        // Llamada al servicio para eliminar al usuario
        boolean isDeleted = userService.deleteByUsername(username);

        if (isDeleted) {
            return ResponseEntity.ok("Usuario eliminado exitosamente.");
        } else {
            return ResponseEntity.status(400).body("No se pudo eliminar el usuario.");
        }
    } catch (Exception e) {
        logger.error("Error al eliminar el usuario: " + e.getMessage(), e);
        return ResponseEntity.status(500).body("Hubo un error al procesar la solicitud.");
    }
}

    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            boolean isDeleted = userService.deleteUserById(id);

            if (isDeleted) {
                Optional<UserSec> user = userService.findById(id);
                user.ifPresent(u -> {
                    try {
                        emailService.sendAccountDeletionEmail(u.getEmail());
                    } catch (MessagingException e) {
                        logger.error("Error sending account deletion email: " + e.getMessage(), e);
                    }
                });
                return ResponseEntity.ok("Usuario eliminado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error al procesar la solicitud.");
        }
    }

        


    }