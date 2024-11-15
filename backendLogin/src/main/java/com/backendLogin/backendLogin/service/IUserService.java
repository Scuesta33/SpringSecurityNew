package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import com.backendLogin.backendLogin.model.UserSec;

//Definición de la interfaz IUserService que declara métodos para manejar operaciones sobre usuarios
public interface IUserService {

 // Método para obtener todos los usuarios desde la base de datos
 List<UserSec> findAll();

 // Método para obtener un usuario específico por su ID
 // Devuelve un Optional, ya que el usuario puede no existir en la base de datos
 Optional<UserSec> findById(Long id);

 // Método para guardar un nuevo usuario en la base de datos
 // Recibe un objeto UserSec y lo guarda, devolviendo el usuario guardado (con su ID asignado)
 UserSec save(UserSec userSec);

 // Método para eliminar un usuario por su ID
 // No devuelve nada, ya que solo realiza la operación de eliminación
 void deleteById(Long id);

 // Método para actualizar los datos de un usuario existente
 // Recibe un objeto UserSec con los nuevos datos y lo actualiza en la base de datos
 void update(UserSec userSec);

 // Método para encriptar la contraseña de un usuario
 // Recibe la contraseña en texto plano y devuelve la contraseña encriptada
 String encriptPassword(String password);
}

