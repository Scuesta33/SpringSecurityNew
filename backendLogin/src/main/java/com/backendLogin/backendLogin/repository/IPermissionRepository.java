package com.backendLogin.backendLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendLogin.backendLogin.model.Permission;

//Define una interfaz que extiende JpaRepository para manejar la entidad 'Permission'.
//JpaRepository es parte de Spring Data JPA y proporciona métodos CRUD predefinidos para interactuar con la base de datos.
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
 // Al extender JpaRepository, esta interfaz hereda automáticamente métodos como:
 // - findById(Long id)  : Busca una entidad 'Permission' por su ID.
 // - findAll()          : Obtiene una lista de todas las entidades 'Permission'.
 // - save(Permission)   : Guarda o actualiza una entidad 'Permission'.
 // - deleteById(Long id): Elimina una entidad 'Permission' por su ID.
 // - count()            : Devuelve el número total de entidades 'Permission' en la base de datos.
}
