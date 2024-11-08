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

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;

    // Definir rolesList como Set<Role> para usar generics
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // El eager va a cargar todos los roles
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> rolesList = new HashSet<>(); // Cambia aquí a Set<Role>

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
    
}

