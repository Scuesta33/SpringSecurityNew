package com.backendLogin.backendLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backendLogin.backendLogin.model.Role;

//Define una interfaz 'IRoleRepository' que extiende de 'JpaRepository' para manejar la entidad 'Role'.
//Al extender 'JpaRepository', 'IRoleRepository' obtiene todos los métodos CRUD básicos sin necesidad de implementar lógica adicional.
public interface IRoleRepository extends JpaRepository<Role, Long> {
	 Optional<Role> findByName(String name);
 // Al extender JpaRepository, la interfaz 'IRoleRepository' tiene acceso a los métodos básicos de JpaRepository como:
 // - findById(Long id): Busca una entidad 'Role' por su ID.
 // - findAll(): Recupera todas las entidades 'Role' de la base de datos.
 // - save(Role role): Guarda una nueva entidad 'Role' o actualiza una existente.
 // - deleteById(Long id): Elimina una entidad 'Role' por su ID.
 // - count(): Devuelve el número total de registros de la entidad 'Role'.
 // Y muchos más métodos adicionales heredados de 'JpaRepository'.
}


