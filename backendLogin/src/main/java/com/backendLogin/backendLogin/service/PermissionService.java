package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.Permission;
import com.backendLogin.backendLogin.repository.IPermissionRepository;

//Clase de servicio que implementa la interfaz IPermissionService
//Esta clase maneja las operaciones relacionadas con los permisos, interactuando con el repositorio correspondiente.
@Service
public class PermissionService implements IPermissionService {

 // Inyección de dependencias: se obtiene el repositorio para interactuar con la base de datos
 @Autowired
 private IPermissionRepository permissionRepository;

 // Método para obtener todos los permisos
 @Override
 public List<Permission> findAll() {
     // Llama al repositorio para obtener todos los permisos almacenados en la base de datos
     return permissionRepository.findAll();
 }

 // Método para obtener un permiso por su ID
 @Override
 public Optional<Permission> findById(Long id) {
     // Llama al repositorio para obtener un permiso específico por su ID
     // Devuelve un Optional para manejar el caso cuando el permiso no exista
     return permissionRepository.findById(id);
 }

 // Método para guardar un nuevo permiso
 @Override
 public Permission save(Permission permission) {
     // Llama al repositorio para guardar el permiso proporcionado
     // El permiso guardado es devuelto, generalmente con un ID asignado por la base de datos
     return permissionRepository.save(permission);
 }

 // Método para eliminar un permiso por su ID
 @Override
 public void deleteById(Long id) {
     // Llama al repositorio para eliminar el permiso con el ID dado
     permissionRepository.deleteById(id);
 }

 // Método para actualizar un permiso existente
 @Override
 public Permission update(Permission permission) {
     // Llama al repositorio para guardar el permiso actualizado
     // Si el permiso ya existe, este se actualiza; si no, se crea uno nuevo
     return permissionRepository.save(permission);
 }
}

