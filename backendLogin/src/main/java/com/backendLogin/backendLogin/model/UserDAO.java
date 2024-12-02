package com.backendLogin.backendLogin.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    // Inyectamos JdbcTemplate (Spring Boot lo configura automáticamente)
    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void eliminarUsuario(String username, String password) throws Exception {
        // Verificamos si el usuario existe y si la contraseña es correcta
        if (verificarUsuario(username, password)) {
            // Eliminar roles y permisos asociados al usuario
            eliminarRoles(username);
            eliminarPermisos(username);

            // Eliminar al usuario de la tabla
            eliminarUsuarioDeTabla(username);
        } else {
            throw new Exception("Contraseña incorrecta para el usuario: " + username);
        }
    }

    private boolean verificarUsuario(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        try {
            String hashedPassword = jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);
            return hashedPassword != null && BCrypt.checkpw(password, hashedPassword);
        } catch (Exception e) {
            // Si el usuario no existe o hay un error en la consulta, retornamos false
            return false;
        }
    }

    public void eliminarRoles(String username) {
        // Eliminamos roles asociados al usuario en la tabla roles_permissions
        String query = "DELETE FROM roles_permissions WHERE username = ?";
        jdbcTemplate.update(query, username);
    }

    public void eliminarPermisos(String username) {
        // Eliminamos permisos asociados al usuario en la tabla user_permissions
        String query = "DELETE FROM user_permissions WHERE username = ?";
        jdbcTemplate.update(query, username);
    }

    private void eliminarUsuarioDeTabla(String username) {
        // Eliminamos al usuario de la tabla users
        String query = "DELETE FROM users WHERE username = ?";
        jdbcTemplate.update(query, username);
    }

    @Transactional
    public void eliminarUsuarioPorId(Long id) throws Exception {
        try {
            // Primero, obtenemos el username del usuario por su ID
            String query = "SELECT username FROM users WHERE id = ?";
            String username = jdbcTemplate.queryForObject(query, new Object[]{id}, String.class);

            if (username == null) {
                throw new Exception("No se encontró usuario con ID: " + id);
            }

            // Eliminamos roles, permisos y usuario por ID
            eliminarRoles(username);
            eliminarPermisos(username);
            eliminarUsuarioDeTabla(username);
        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario con ID: " + id, e);
        }
    }
}