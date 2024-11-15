package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.Role;
import com.backendLogin.backendLogin.repository.IRoleRepository;

//Clase de servicio que implementa la interfaz IRoleService
//Esta clase maneja las operaciones relacionadas con los roles, interactuando con el repositorio correspondiente.
@Service
public class RoleService implements IRoleService {

 // Inyección de dependencias: se obtiene el repositorio para interactuar con la base de datos
 @Autowired
 private IRoleRepository roleRepository;

 // Método para obtener todos los roles
 @Override
 public List<Role> findAll() {
     // Llama al repositorio para obtener todos los roles almacenados en la base de datos
     return roleRepository.findAll();
 }

 // Método para obtener un rol por su ID
 @Override
 public Optional<Role> findById(Long id) {
     // Llama al repositorio para obtener un rol específico por su ID
     // Devuelve un Optional para manejar el caso cuando el rol no exista
     return roleRepository.findById(id);
 }

 // Método para guardar un nuevo rol
 @Override
 public Role save(Role role) {
     // Llama al repositorio para guardar el rol proporcionado
     // El rol guardado es devuelto, generalmente con un ID asignado por la base de datos
     return roleRepository.save(role);
 }

 // Método para eliminar un rol por su ID
 @Override
 public void deleteById(Long id) {
     // Llama al repositorio para eliminar el rol con el ID dado
     roleRepository.deleteById(id);
 }

 // Método para actualizar un rol existente
 @Override
 public Role update(Role role) {
     // Llama al repositorio para guardar el rol actualizado
     // Si el rol ya existe, este se actualiza; si no, se crea uno nuevo
     return roleRepository.save(role);
 }
}

