package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;


import com.backendLogin.backendLogin.model.Role;

public interface IRoleService {

	List<Role> findAll();
	Optional<Role> findById(Long id);
	Role save (Role role);
	void deleteById(Long id);
	Role update(Role role);
}
