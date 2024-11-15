package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;


import com.backendLogin.backendLogin.model.Role;

//Definición de la interfaz IRoleService que declara métodos para manejar operaciones sobre roles
public interface IRoleService {

 // Método para obtener todos los roles desde la base de datos
 List<Role> findAll();

 // Método para obtener un rol específico por su ID
 // Devuelve un Optional, ya que el rol puede no existir en la base de datos
 Optional<Role> findById(Long id);

 // Método para guardar un nuevo rol en la base de datos
 // Recibe un objeto Role y lo guarda, devolviendo el rol guardado (con su ID asignado)
 Role save(Role role);

 // Método para eliminar un rol por su ID
 // No devuelve nada, ya que solo realiza la operación de eliminación
 void deleteById(Long id);

 // Método para actualizar un rol existente
 // Recibe un objeto Role con los nuevos datos y lo actualiza en la base de datos
 Role update(Role role);
}

