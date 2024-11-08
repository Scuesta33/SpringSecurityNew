package com.backendLogin.backendLogin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private IUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//tenemos nuestro UserSec y necesitamos devolverlo en formato UserDetails
		//traer nuestro usuario de la base de datos
		UserSec userSec = userRepo.findUserEntityByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no fue encontrado"));
		//creamos una lista para los Permisos
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		/// traer roles y convertirlos en SimpleGrantedAuthority
		
		userSec.getRolesList()
		       .forEach(role -> authorityList.add (new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));
		
		
		
		
		//traer permisos y convertirlos en SimpleGrantedAuthority
		userSec.getRolesList().stream()
		       .flatMap(role -> role.getPermissionsList().stream())
		       .forEach(permission -> authorityList.add (new SimpleGrantedAuthority(permission.getPermissionName())));
		
		return new User(
				userSec.getUsername(),
				userSec.getPassword(),
				userSec.isEnabled(),
		        userSec.isAccountNotExpired(),
				userSec.isCredentialNotExpired(),
				userSec.isAccountNotLocked(),
				authorityList
			);
		
	}
	
	}
