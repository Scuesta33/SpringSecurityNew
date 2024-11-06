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

import com.backendLogin.backendLogin.model.Role;
import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.service.IRoleService;
import com.backendLogin.backendLogin.service.IUserService;

public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@GetMapping
	public ResponseEntity<List<UserSec>> getAllUsers(){
		List<UserSec> users = userService.findAll();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserSec> getPermissionById (@PathVariable Long id){
		Optional<UserSec> user = userService.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
		
		Set<Role> roleList = new HashSet<Role>();
		Role readRole;
		
		for(Role role: userSec.getRolesList()) {
			readRole = roleService.findById(role.getId()).orElse(null);
			if(readRole != null) {
				roleList.add(readRole);
			}
		}
		
		if(!roleList.isEmpty()) {
			userSec.setRolesList(roleList);
			
			UserSec newUser = userService.save(userSec);
			return ResponseEntity.ok(newUser);
		}
		return null;
	}
}
