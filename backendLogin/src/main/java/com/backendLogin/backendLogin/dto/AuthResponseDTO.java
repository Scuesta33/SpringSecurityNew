package com.backendLogin.backendLogin.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponseDTO (String username,
		                       String message,
		                       String jwt,
		                       Boolean status) {

}
