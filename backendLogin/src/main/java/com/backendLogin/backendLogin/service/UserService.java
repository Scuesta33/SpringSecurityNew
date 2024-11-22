package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;  // Repositorio para acceder a los usuarios

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Inyectamos el encoder de contraseñas

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
    public UserSec updateUser(String username, String newPassword) throws Exception {
        // Verificar si el nombre de usuario es nulo o vacío
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        // Buscar al usuario por username
        Optional<UserSec> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new Exception("User not found with username: " + username);
        }

        UserSec user = optionalUser.get();

        // Si la nueva contraseña es proporcionada, encriptarla y actualizarla
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));  // Asumimos que passwordEncoder está inyectado
        }

        return userRepository.save(user);  // Guardar al usuario con los cambios
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
	public void update(UserSec userSec) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserSec updateUser(Long id, String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}

