package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import com.backendLogin.backendLogin.model.Permission;

//Definición de la interfaz IPermissionService que declara métodos para manejar operaciones sobre permisos
public interface IPermissionService {

 // Método para obtener todos los permisos desde la base de datos
 List<Permission> findAll();

 // Método para obtener un permiso específico por su ID
 // Devuelve un Optional, ya que el permiso puede no existir en la base de datos
 Optional<Permission> findById(Long id);

 // Método para guardar un nuevo permiso en la base de datos
 // Recibe un objeto Permission y lo guarda, devolviendo el permiso guardado (con su ID asignado)
 Permission save(Permission permission);

 // Método para eliminar un permiso por su ID
 // No devuelve nada, ya que solo realiza la operación de eliminación
 void deleteById(Long id);

 // Método para actualizar un permiso existente
 // Recibe un objeto Permission con los nuevos datos y lo actualiza en la base de datos
 Permission update(Permission permission);
}

