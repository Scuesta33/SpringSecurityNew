package com.backendLogin.backendLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendLogin.backendLogin.model.UserSec;

public interface IUserRepository extends JpaRepository< UserSec, Long> {
	Optional<UserSec> findUserEntityByUsername(String username);

}
