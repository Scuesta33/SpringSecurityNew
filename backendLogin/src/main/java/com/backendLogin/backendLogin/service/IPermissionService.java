package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import com.backendLogin.backendLogin.model.Permission;

public interface IPermissionService {

	List<Permission> findAll();
	Optional<Permission> findById(Long id);
	Permission save (Permission permission);
	void deleteById(Long id);
	Permission update(Permission permission);
}