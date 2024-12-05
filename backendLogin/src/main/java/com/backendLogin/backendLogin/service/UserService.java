package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserDAO;
import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;  // Repositorio para acceder a los usuarios

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Inyectamos el encoder de contraseñas
    
    @Autowired  // Inyección del DAO de usuario
    private UserDAO userDAO;
    
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
    	
    	// Verificar si el nombre de usuario es nulo o vacío
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        // Buscar al usuario por username
        Optional<UserSec> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found with username: " + username);
        }

        UserSec user = optionalUser.get();

        // Si se proporciona un nuevo nombre de usuario, actualizarlo
        if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(user.getUsername())) {
            Optional<UserSec> existingUser = userRepository.findByUsername(newUsername);
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(newUsername); // Solo actualiza si el nombre de usuario es diferente
        }

        // Si se proporciona una nueva contraseña, encriptarla y actualizarla
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));  // Asumimos que passwordEncoder está inyectado
        }

        // Si se proporciona un nuevo correo electrónico, actualizarlo
        if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(user.getEmail())) {
            // Verificar si el nuevo correo electrónico está disponible
            Optional<UserSec> existingEmailUser = userRepository.findByEmail(newEmail);
            if (existingEmailUser.isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(newEmail);
        }

        // Guardar al usuario con los cambios
        return userRepository.save(user);
    }

    @Override
    public String encriptPassword(String password) {
        return passwordEncoder.encode(password);  // Usamos el BCryptPasswordEncoder para encriptar la contraseña
    }

    @Override
    public Optional<UserSec> findByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String username) {
        try {
            // Buscar al usuario por nombre de usuario
            Optional<UserSec> optionalUser = userRepository.findByUsername(username);

            // Verificar si el usuario existe
            if (optionalUser.isPresent()) {
                UserSec user = optionalUser.get();

                // Desvincular al usuario de los roles en la tabla user_roles
                if (user.getRolesList() != null) {
                    user.getRolesList().clear(); // Elimina la relación, pero no borra los roles
                }

                // Eliminar el usuario de la tabla UserSec
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
            // Verificamos si el usuario existe en la base de datos
            Optional<UserSec> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return false;  // El usuario no existe
            }

            UserSec user = userOptional.get();

            // Eliminar la relación del usuario con los roles
            if (user.getRolesList() != null) {
                user.getRolesList().clear(); // Elimina la relación, pero no borra los roles
            }

            // Eliminar el usuario de la tabla UserSec
            userRepository.delete(user);

            return true;  // El usuario fue eliminado con éxito
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
	    return userRepository.findByEmail(email); // Esto es correcto
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


	}

	


	

