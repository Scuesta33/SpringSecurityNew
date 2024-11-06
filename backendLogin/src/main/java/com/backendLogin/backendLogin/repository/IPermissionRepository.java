package com.backendLogin.backendLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendLogin.backendLogin.model.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Long>{
}
