package com.backendLogin.backendLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendLogin.backendLogin.model.UserSec;

//Define la interfaz 'IUserRepository' que extiende de 'JpaRepository' para manejar la entidad 'UserSec'.
//'JpaRepository' proporciona métodos CRUD básicos para interactuar con la base de datos sin necesidad de implementar la lógica manualmente.
public interface IUserRepository extends JpaRepository<UserSec, Long> {

 // Método personalizado que busca una entidad 'UserSec' por su nombre de usuario ('username').
 // 'Optional<UserSec>' se usa para evitar un NullPointerException si no se encuentra un usuario con ese nombre.
 Optional<UserSec> findUserEntityByUsername(String username);
}

