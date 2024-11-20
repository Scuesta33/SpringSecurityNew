package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

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
    public void update(UserSec userSec) {
        save(userSec);
    }

    @Override
    public String encriptPassword(String password) {
        return passwordEncoder.encode(password);  // Usamos el BCryptPasswordEncoder para encriptar la contraseña
    }

    @Override
    public Optional<UserSec> findByName(String username) {
        return userRepository.findByUsername(username);
    }
}

