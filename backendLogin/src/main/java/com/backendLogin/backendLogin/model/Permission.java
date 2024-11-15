package com.backendLogin.backendLogin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  // Anotación de JPA que marca esta clase como una entidad persistente.
@Getter @Setter  // Lombok: Genera automáticamente los métodos getters y setters para todos los campos.
@AllArgsConstructor  // Lombok: Genera un constructor con todos los campos como parámetros.
@NoArgsConstructor   // Lombok: Genera un constructor sin parámetros.
@Table(name="permissions")  // Especifica el nombre de la tabla en la base de datos que esta entidad representa.
public class Permission {

    @Id  // Anotación que marca el campo como la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La clave primaria se genera automáticamente, con auto-incremento.
    private Long id;  // El ID único del permiso.

    @Column(unique = true, nullable = false)  // Anotación que marca el campo como columna en la base de datos.
    // El atributo 'unique = true' asegura que los valores de esta columna sean únicos en la base de datos.
    // 'nullable = false' asegura que el campo no puede ser nulo (obligatorio).
    private String permissionName;  // Nombre del permiso, como "READ", "WRITE", "ADMIN", etc.

    // Métodos getter y setter generados por Lombok debido a las anotaciones @Getter y @Setter, pero aquí se incluyen manualmente para mayor claridad.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
