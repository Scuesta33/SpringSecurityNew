package com.backendLogin.backendLogin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendLogin.backendLogin.model.Permission;
import com.backendLogin.backendLogin.service.IPermissionService;

@RestController  // Marca esta clase como un controlador REST
@RequestMapping("/api/permissions")  // Define la ruta base de los endpoints en esta clase
public class PermissionController {

    // Inyección del servicio que maneja la lógica de negocio para los permisos
    @Autowired
    private IPermissionService permissionService;

    /**
     * Endpoint para obtener todos los permisos.
     * Responde con una lista de permisos existentes en el sistema.
     *
     * @return ResponseEntity con el código de estado 200 y la lista de permisos.
     */
    @GetMapping  // Este método maneja solicitudes GET a la ruta '/api/permissions'
    public ResponseEntity<List<Permission>> getAllPermissions() {
        // Obtiene todos los permisos a través del servicio
        List<Permission> permissions = permissionService.findAll();

        // Devuelve una respuesta con código 200 OK y la lista de permisos en el cuerpo
        return ResponseEntity.ok(permissions);
    }

    /**
     * Endpoint para obtener un permiso por su ID.
     * Si el permiso existe, responde con el permiso. Si no, responde con 404 Not Found.
     *
     * @param id El ID del permiso a recuperar.
     * @return ResponseEntity con el código de estado 200 y el permiso si se encuentra, o 404 si no se encuentra.
     */
    @GetMapping("/{id}")  // Este método maneja solicitudes GET a la ruta '/api/permissions/{id}'
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        // Busca el permiso por su ID a través del servicio
        Optional<Permission> permission = permissionService.findById(id);

        // Si el permiso existe, lo devuelve con código 200 OK, de lo contrario, devuelve 404 Not Found
        return permission.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para crear un nuevo permiso.
     * Recibe un objeto Permission en el cuerpo de la solicitud y lo guarda en la base de datos.
     *
     * @param permission El permiso que se quiere crear.
     * @return ResponseEntity con el código de estado 200 OK y el permiso recién creado.
     */
    @PostMapping  // Este método maneja solicitudes POST a la ruta '/api/permissions'
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        // Guarda el nuevo permiso en la base de datos a través del servicio
        Permission newPermission = permissionService.save(permission);

        // Devuelve una respuesta con código 200 OK y el nuevo permiso en el cuerpo
        return ResponseEntity.ok(newPermission);
    }
}
