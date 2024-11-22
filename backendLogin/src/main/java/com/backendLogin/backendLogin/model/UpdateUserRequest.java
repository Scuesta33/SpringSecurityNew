package com.backendLogin.backendLogin.model;

public class UpdateUserRequest {
    private String newUsername;
    private String newPassword;

    // Getters y setters

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

