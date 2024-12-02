package com.backendLogin.backendLogin.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

@Entity  // Marca la clase como una entidad JPA que será mapeada a una tabla en la base de datos.
@Table(name = "users")  // Especifica que la clase 'UserSec' se mapea a la tabla 'users' en la base de datos.
@Getter  // Lombok: Genera automáticamente los métodos getter para todos los campos.
@Setter  // Lombok: Genera automáticamente los métodos setter para todos los campos.
@AllArgsConstructor  // Lombok: Genera un constructor con todos los campos como parámetros.
@NoArgsConstructor   // Lombok: Genera un constructor vacío (sin parámetros).
public class UserSec {

    @Id  // Marca el campo 'id' como la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La clave primaria se genera automáticamente (auto-incremento).
    private Long id;  // El identificador único del usuario (clave primaria).

    private String username;  // El nombre de usuario para autenticación.

    private String password;  // La contraseña del usuario, normalmente encriptada.

    private boolean enabled;  // Indica si la cuenta del usuario está habilitada (si puede iniciar sesión).
    
  

    private boolean accountNotExpired;  // Indica si la cuenta del usuario ha expirado (por ejemplo, debido a inactividad).

    private boolean accountNotLocked;  // Indica si la cuenta del usuario está bloqueada.

    private boolean credentialNotExpired;  // Indica si las credenciales del usuario (como la contraseña) han expirado.
    
    @Column(unique = true)
    private String email;

    

	// Relación muchos a muchos entre 'UserSec' y 'Role' (un usuario puede tener múltiples roles y un rol puede ser asignado a múltiples usuarios).
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // Relación de muchos a muchos.
    // EAGER carga los roles asociados inmediatamente al obtener un usuario. 
    // CascadeType.ALL significa que cualquier cambio en el usuario (guardar, eliminar) se reflejará en los roles asociados.
    @JoinTable(  // Define la tabla intermedia 'user_roles' para gestionar la relación muchos a muchos.
        name = "user_roles",  // Nombre de la tabla intermedia.
        joinColumns = @JoinColumn(name = "user_id"),  // Columna en la tabla intermedia que referencia a 'UserSec'.
        inverseJoinColumns = @JoinColumn(name = "role_id")  // Columna en la tabla intermedia que referencia a 'Role'.
    )
    private Set<Role> rolesList = new HashSet<>();  // Lista de roles asignados al usuario. Usamos un Set para evitar duplicados.

    // Métodos getter y setter manuales, aunque Lombok generaría estos automáticamente.
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNotExpired() {
        return accountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public boolean isAccountNotLocked() {
        return accountNotLocked;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public boolean isCredentialNotExpired() {
        return credentialNotExpired;
    }

    public void setCredentialNotExpired(boolean credentialNotExpired) {
        this.credentialNotExpired = credentialNotExpired;
    }

    public Set<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(Set<Role> rolesList) {
        this.rolesList = rolesList;
    }
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

