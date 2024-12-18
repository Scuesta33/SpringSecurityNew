
package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        return userRepository.save(userSec);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSec updateUser(String username, String newUsername, String newPassword, String newEmail) throws Exception {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        Optional<UserSec> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found with username: " + username);
        }

        UserSec user = optionalUser.get();

        if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(user.getUsername())) {
            Optional<UserSec> existingUser = userRepository.findByUsername(newUsername);
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(newUsername);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(user.getEmail())) {
            Optional<UserSec> existingEmailUser = userRepository.findByEmail(newEmail);
            if (existingEmailUser.isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(newEmail);
        }

        return userRepository.save(user);
    }

    @Override
    public String encriptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public Optional<UserSec> findByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String username) {
        try {
            Optional<UserSec> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isPresent()) {
                UserSec user = optionalUser.get();

                if (user.getRolesList() != null) {
                    user.getRolesList().clear();
                }

                userRepository.delete(user);
                return true;
            } else {
                logger.warn("No se encontró el usuario con nombre de usuario: " + username);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long id) {
        try {
            Optional<UserSec> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return false;
            }

            UserSec user = userOptional.get();

            if (user.getRolesList() != null) {
                user.getRolesList().clear();
            }

            userRepository.delete(user);

            return true;
        } catch (Exception e) {
            logger.error("Error al eliminar el usuario con ID: " + id, e);
            return false;
        }
    }

    @Override
    public void update(UserSec userSec) {
        // TODO Auto-generated method stub
    }

    @Override
    public UserSec updateUser(Long id, String username, String password) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteByUsernameAndPassword(String username, String password) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<UserSec> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserSec updateUser(String newUsername, String newPassword) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserSec updateUser(String newUsername, String newPassword, String newEmail) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    // Registra o actualiza un usuario OAuth2
    public UserSec registerOrUpdateOAuthUser(String provider, String email, String username) {
        Optional<UserSec> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            // Si el usuario existe, actualiza el nombre de usuario (si es necesario)
            UserSec user = existingUser.get();
            if (!user.getUsername().equals(username)) {
                user.setUsername(username);
            }
            return userRepository.save(user);
        } else {
            // Si el usuario no existe, crea uno nuevo
            UserSec newUser = new UserSec();
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setProvider(provider);
            newUser.setEnabled(true);
            newUser.setAccountNotExpired(true);
            newUser.setAccountNotLocked(true);
            newUser.setCredentialNotExpired(true);
            return userRepository.save(newUser);
        }
    }
}
