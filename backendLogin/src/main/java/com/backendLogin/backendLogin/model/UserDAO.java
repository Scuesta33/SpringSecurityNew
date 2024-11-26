package com.backendLogin.backendLogin.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void eliminarUsuario(String username, String password) throws SQLException {
        // Iniciar una transacción
        connection.setAutoCommit(false);

        try {
            // Verificar si el usuario existe y si la contraseña es correcta
            if (verificarUsuario(username, password)) {
                // Eliminar roles y permisos asociados al usuario
                eliminarRoles(username);
                eliminarPermisos(username);

                // Eliminar al usuario de la tabla
                eliminarUsuarioDeTabla(username);

                // Commit si todo fue exitoso
                connection.commit();
            } else {
                // Si la contraseña no es correcta, revertir los cambios
                throw new SQLException("Contraseña incorrecta");
            }
        } catch (SQLException e) {
            connection.rollback();  // Rollback si hay algún error
            throw e;  // Lanza la excepción para manejo posterior
        } finally {
            connection.setAutoCommit(true); // Restaurar el comportamiento por defecto
        }
    }

    private boolean verificarUsuario(String username, String password) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Recuperamos el hash de la contraseña de la base de datos
                String hashedPassword = rs.getString("password");

                // Verificamos si la contraseña proporcionada coincide con el hash
                return BCrypt.checkpw(password, hashedPassword);
            }
            return false; // Usuario no encontrado
        }
    }

    private void eliminarRoles(String username) throws SQLException {
        String query = "DELETE FROM roles_permissions WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }

    private void eliminarPermisos(String username) throws SQLException {
        String query = "DELETE FROM user_permissions WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }

    private void eliminarUsuarioDeTabla(String username) throws SQLException {
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }
}
