package com.security.domain;

public class LoginRequest {
	private String username;
	private String password;
	
	public LoginRequest() {
		System.out.println("Inside LoginRequest default constructor");
	}
	
	public LoginRequest(String username, String password) {
		this.username= username;
		this.password= password;
		System.out.println("Inside LoginRequest(String username, String password) constructor");
	}
	
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
