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

import com.backendLogin.backendLogin.model.Permission;
import com.backendLogin.backendLogin.model.Role;
import com.backendLogin.backendLogin.service.IPermissionService;
import com.backendLogin.backendLogin.service.IRoleService;

@RestController  // Indica que esta clase es un controlador REST que manejará solicitudes HTTP y devolverá respuestas en formato JSON (por defecto)
@RequestMapping("/api/roles")  // Define la ruta base para todas las solicitudes en este controlador, en este caso "/api/roles"
public class RoleController {

    @Autowired  // Inyecta automáticamente el servicio IRoleService, que manejará las operaciones relacionadas con los roles
    private IRoleService roleService;

    @Autowired  // Inyecta automáticamente el servicio IPermissionService, que manejará las operaciones relacionadas con los permisos
    private IPermissionService permiService;

    // Método para manejar solicitudes GET a la ruta "/api/roles" y obtener todos los roles
    @GetMapping  
    public ResponseEntity<List<Role>> getAllRoles() {
        // Llama al servicio roleService para obtener todos los roles de la base de datos
        List<Role> roles = roleService.findAll();
        // Devuelve una respuesta HTTP con código 200 (OK) y la lista de roles en el cuerpo de la respuesta
        return ResponseEntity.ok(roles);
    }

    // Método para manejar solicitudes GET a la ruta "/api/roles/{id}" y obtener un rol específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        // Busca un rol por su ID usando el servicio roleService
        Optional<Role> role = roleService.findById(id);
        // Si el rol existe, devuelve una respuesta HTTP 200 (OK) con el rol. Si no, devuelve una respuesta 404 (Not Found)
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para manejar solicitudes POST a la ruta "/api/roles" y crear un nuevo rol
    @PostMapping  
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        // Crea un conjunto de permisos para asociar con el rol
        Set<Permission> permiList = new HashSet<Permission>();
        Permission readPermission;

        // Recorre los permisos del rol recibido en la solicitud
        for (Permission per : role.getPermissionsList()) {
            // Busca cada permiso por su ID usando el servicio permiService
            readPermission = permiService.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                // Si el permiso existe, lo añade al conjunto de permisos del rol
                permiList.add(readPermission);
            }
        }

        // Asocia el conjunto de permisos al rol
        role.setPermissionsList(permiList);
        // Guarda el nuevo rol usando el servicio roleService
        Role newRole = roleService.save(role);
        // Devuelve una respuesta HTTP 200 (OK) con el rol recién creado en el cuerpo de la respuesta
        return ResponseEntity.ok(newRole);
    }
}
