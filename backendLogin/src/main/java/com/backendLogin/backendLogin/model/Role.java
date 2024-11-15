package com.backendLogin.backendLogin.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  // Marca la clase como una entidad JPA que se mapea a una tabla en la base de datos.
@Getter @Setter  // Lombok: Genera automáticamente los métodos getters y setters para todos los campos.
@AllArgsConstructor  // Lombok: Genera un constructor con todos los campos como parámetros.
@NoArgsConstructor   // Lombok: Genera un constructor vacío (sin parámetros).
@Table(name = "roles")  // Especifica el nombre de la tabla en la base de datos a la que esta entidad se mapea.
public class Role {

    @Id  // Marca el campo 'id' como la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La clave primaria se genera automáticamente (auto-incremento).
    private Long id;  // El identificador único del rol (clave primaria).

    private String role;  // El nombre del rol (por ejemplo, "ADMIN", "USER", etc.).

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // Relación muchos a muchos con la entidad Permission. Eager indica que los permisos asociados a un rol se cargarán inmediatamente (de forma anticipada) cuando se obtenga el rol.
    @JoinTable(  // Define la tabla intermedia que se usará para la relación muchos a muchos.
        name = "roles_permissions",  // Nombre de la tabla intermedia.
        joinColumns = @JoinColumn(name = "role_id"),  // Columna de la tabla intermedia que referencia a 'Role'.
        inverseJoinColumns = @JoinColumn(name = "permission_id")  // Columna que referencia a 'Permission'.
    )
    private Set<Permission> permissionsList = new HashSet<>();  // Un rol tiene una lista de permisos. Usamos un Set para evitar duplicados.

    // Métodos getter y setter manuales, aunque Lombok generaría estos automáticamente.
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Permission> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(Set<Permission> permissionsList) {
        this.permissionsList = permissionsList;
    }
}

