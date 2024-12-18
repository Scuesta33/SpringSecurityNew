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

@Entity  // Marks the class as a JPA entity, mapped to a database table.
@Table(name = "users")  // Specifies that this class maps to the 'users' table.
@Getter  // Lombok: Generates getters automatically for all fields.
@Setter  // Lombok: Generates setters automatically for all fields.
@AllArgsConstructor  // Lombok: Generates a constructor with all fields.
@NoArgsConstructor   // Lombok: Generates an empty constructor (no parameters).
public class UserSec {

    @Id  // Marks the 'id' field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment strategy for primary key generation.
    private Long id;  // The user's unique identifier (primary key).

    private String username;  // Username used for authentication.

    private String password;  // User's password (typically encrypted).

    private boolean enabled;  // Indicates whether the user's account is enabled.

    private boolean accountNotExpired;  // Indicates whether the user's account has expired.

    private boolean accountNotLocked;  // Indicates whether the user's account is locked.

    private boolean credentialNotExpired;  // Indicates whether the user's credentials (password) have expired.

    @Column(unique = true)  // Ensures that the email is unique in the database.
    private String email;  // User's email address.

    // New field to store OAuth provider information (Google, Facebook, etc.)
    private String provider;  // The OAuth provider (e.g., "google", "facebook").

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)  // Many-to-many relationship with roles.
    @JoinTable(  // Defines the join table for the many-to-many relationship.
        name = "user_roles",  // The name of the join table.
        joinColumns = @JoinColumn(name = "user_id"),  // The column that references the 'UserSec' entity.
        inverseJoinColumns = @JoinColumn(name = "role_id")  // The column that references the 'Role' entity.
    )
    private Set<Role> rolesList = new HashSet<>();  // Set of roles assigned to the user.

    // Getter and setter methods for the 'provider' field.
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    // Other getter and setter methods for existing fields.

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
