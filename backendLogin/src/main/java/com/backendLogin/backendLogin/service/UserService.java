package com.backendLogin.backendLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendLogin.backendLogin.model.UserSec;
import com.backendLogin.backendLogin.repository.IUserRepository;

@Service // Anotación que indica que esta clase es un servicio de Spring, que será gestionado como un bean dentro del contenedor de Spring.
public class UserService implements IUserService { // La clase implementa la interfaz IUserService, que define los métodos que maneja el servicio de usuarios.

	@Autowired
	private IUserRepository userRepository;  // Inyección de dependencias: se inyecta el repositorio que maneja las operaciones CRUD sobre los usuarios en la base de datos.

	@Override
	public List<UserSec> findAll() {  // Método para obtener todos los usuarios de la base de datos.
		return userRepository.findAll();  // Llama al método `findAll` del repositorio, que devuelve una lista de todos los usuarios.
	}

	@Override
	public Optional<UserSec> findById(Long id) {  // Método para obtener un usuario por su ID.
		return userRepository.findById(id);  // Llama al método `findById` del repositorio, que devuelve un `Optional` con el usuario encontrado o vacío.
	}

	@Override
	public UserSec save(UserSec userSec) {  // Método para guardar un usuario en la base de datos.
		return userRepository.save(userSec);  // Llama al método `save` del repositorio para persistir el usuario.
	}

	@Override
	public void deleteById(Long id) {  // Método para eliminar un usuario por su ID.
		userRepository.deleteById(id);  // Llama al método `deleteById` del repositorio para eliminar el usuario de la base de datos.
	}

	@Override
	public void update(UserSec userSec) {  // Método para actualizar un usuario en la base de datos.
       save(userSec);  // Aquí reutiliza el método `save` para actualizar al usuario. Si el ID del usuario existe, se actualiza; si no, se guarda como uno nuevo.
	}

	@Override
	public String encriptPassword(String password) {  // Método para encriptar una contraseña.
		// Utiliza BCryptPasswordEncoder para encriptar la contraseña y devolverla.
		return new BCryptPasswordEncoder().encode(password);  // Crea un nuevo codificador BCrypt y codifica la contraseña.
	}

}

