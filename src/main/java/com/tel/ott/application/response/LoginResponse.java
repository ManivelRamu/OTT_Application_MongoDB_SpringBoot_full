package com.tel.ott.application.response;

public class LoginResponse {

    private int status;
    private String message;
    private String role;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(int status, String message, String role, String token) {
        this.status = status;
        this.message = message;
        this.role = role;
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
