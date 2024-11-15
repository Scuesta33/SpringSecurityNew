package com.backendLogin.backendLogin.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.model.Role;
import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.service.IRoleService;
import com.backendLogin.backendLogin.service.IUserService;
//Anotación que convierte esta clase en un controlador REST y especifica la ruta base "/api/users"
@RestController
@RequestMapping("/api/users")
public class UserController {

 @Autowired
 private IUserService userService;  // Servicio para manejar la lógica de usuarios

 @Autowired
 private IRoleService roleService;  // Servicio para manejar la lógica de roles

 // Método para manejar solicitudes GET a la ruta "/api/users" y obtener todos los usuarios
 @GetMapping
 public ResponseEntity<List<UserSec>> getAllUsers(){
     List<UserSec> users = userService.findAll();  // Llama al servicio para obtener todos los usuarios
     return ResponseEntity.ok(users);  // Devuelve una respuesta HTTP 200 (OK) con la lista de usuarios
 }

 // Método para manejar solicitudes GET a la ruta "/api/users/{id}" y obtener un usuario por su ID
 @GetMapping("/{id}")
 public ResponseEntity<UserSec> getUserById(@PathVariable Long id){
     Optional<UserSec> user = userService.findById(id);  // Busca el usuario por su ID
     // Si el usuario se encuentra, devuelve una respuesta 200 (OK), si no, devuelve 404 (Not Found)
     return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
 }

 // Método para manejar solicitudes POST a la ruta "/api/users" y crear un nuevo usuario
 @PostMapping
 public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
     
     Set<Role> roleList = new HashSet<Role>();  // Crea un conjunto vacío de roles para asociar al usuario
     Role readRole;  // Variable temporal para almacenar un rol encontrado
     
     // Encriptamos la contraseña del usuario antes de guardarlo
     userSec.setPassword(userService.encriptPassword(userSec.getPassword()));  // Llama al servicio para encriptar la contraseña
     
     // Recorre la lista de roles proporcionados en la solicitud y valida si existen en la base de datos
     for(Role role: userSec.getRolesList()) {
         // Busca el rol por su ID usando el servicio roleService. Si no se encuentra, se asigna null.
         readRole = roleService.findById(role.getId()).orElse(null);  
         // Si el rol existe (no es null), lo agrega al conjunto de roles del usuario
         if(readRole != null) {
             roleList.add(readRole);  // Agrega el rol válido al conjunto
         }
     }
     
     // Si la lista de roles no está vacía (es decir, el usuario tiene al menos un rol válido)
     if(!roleList.isEmpty()) {
         userSec.setRolesList(roleList);  // Asocia la lista de roles válidos al usuario
         UserSec newUser = userService.save(userSec);  // Guarda el usuario en la base de datos
         return ResponseEntity.ok(newUser);  // Devuelve una respuesta 200 (OK) con el usuario recién creado
     }
     // Si no se han encontrado roles válidos, no se crea el usuario y se retorna null
     return null;  
 }
}