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
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.dto.RegisterDTO;
import com.backendLogin.backendLogin.dto.UserSecUpdateRequest;
import com.backendLogin.backendLogin.model.Role;
import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.service.EmailService;
import com.backendLogin.backendLogin.service.IRoleService;
import com.backendLogin.backendLogin.service.IUserService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Validated RegisterDTO registerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid data provided"));
        }

        if (registerDTO.getPassword() == null || registerDTO.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password cannot be empty"));
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

            UserSec updatedUser = userService.updateUser(
                username,
                updateRequest.getNewUsername(),
                updateRequest.getNewPassword(),
                updateRequest.getNewEmail()
            );

            boolean isEmailUpdated = !updatedUser.getEmail().equals(updateRequest.getNewEmail());
            boolean isUsernameUpdated = !updatedUser.getUsername().equals(updateRequest.getNewUsername());
            boolean isPasswordUpdated = !updatedUser.getPassword().equals(updateRequest.getNewPassword());

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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Optional<UserSec> user = userService.findByName(username);
            if (!user.isPresent()) {
                return ResponseEntity.status(400).body("Usuario no encontrado.");
            }

            emailService.sendAccountDeletionEmail(user.get().getEmail());

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
            Optional<UserSec> user = userService.findById(id);

            if (user.isPresent()) {
                boolean isDeleted = userService.deleteUserById(id);

                if (isDeleted) {
                    try {
                        emailService.sendAccountDeletionEmail(user.get().getEmail());
                    } catch (MessagingException e) {
                        logger.error("Error sending account deletion email: " + e.getMessage(), e);
                    }
                    return ResponseEntity.ok("Usuario eliminado exitosamente.");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error al procesar la solicitud.");
        }
    }

    // Manejo de la autenticación OAuth2 (Google)
    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<Map<String, Object>> getUserInfo(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = oauth2User.getAttribute("name");
            String email = oauth2User.getAttribute("email");

            // Registra o actualiza el usuario con la información de OAuth2
            userService.registerOrUpdateOAuthUser("google", email, username);

            return ResponseEntity.ok(Map.of("message", "User authenticated", "user", oauth2User.getAttributes()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Not authenticated"));
    }
}
