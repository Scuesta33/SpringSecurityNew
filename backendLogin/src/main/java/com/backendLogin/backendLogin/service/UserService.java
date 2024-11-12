package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

@Service
public class UserService implements IUserService {
   
	@Autowired
	private IUserRepository userRepository;
	@Override
	public List<UserSec> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<UserSec> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public UserSec save(UserSec userSec) {
		return userRepository.save(userSec);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public void update(UserSec userSec) {
       save(userSec);
		
	}

	@Override
	public String encriptPassword(String password) {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder().encode(password);
	}

}