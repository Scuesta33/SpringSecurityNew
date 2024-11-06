package com.backendLogin.backendLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.backendLogin.backendLogin.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long>{
	
}


