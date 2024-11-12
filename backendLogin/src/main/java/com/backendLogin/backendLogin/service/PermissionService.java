package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.Permission;
import com.backendLogin.backendLogin.repository.IPermissionRepository;

@Service
public class PermissionService implements IPermissionService {

	@Autowired
	private IPermissionRepository permissionRepository;
	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public Optional<Permission> findById(Long id) {
		return permissionRepository.findById(id);
	}

	@Override
	public Permission save(Permission permission) {
		return permissionRepository.save(permission);
	}

	@Override
	public void deleteById(Long id) {
		permissionRepository.deleteById(id);
		
	}

	@Override
	public Permission update(Permission permission) {
		return permissionRepository.save(permission);
	}

	
	
}