package com.backendLogin.backendLogin.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {
	@NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

  
}
  
  

