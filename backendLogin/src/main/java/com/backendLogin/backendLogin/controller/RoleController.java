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

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permiService;
	
	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles(){
		List<Role> roles = roleService.findAll();
		return ResponseEntity.ok(roles);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable Long id){
		Optional<Role> role = roleService.findById(id);
		return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		Set<Permission> permiList = new HashSet<Permission>();
		Permission readPermission;
		
		//Recuperar la permission por u ID
		for (Permission per : role.getPermissionsList()) {
			readPermission = permiService.findById(per.getId()).orElse(null);
			if (readPermission != null) {
				permiList.add(readPermission);
			}
		}
		
		role.setPermissionsList(permiList);
		Role newRole = roleService.save(role);
		return ResponseEntity.ok(newRole);
	}
}
