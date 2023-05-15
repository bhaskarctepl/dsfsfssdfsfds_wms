package com.hillspet.wearables.dto;

public class CustomUserDetails {
/*public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	*//**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String firstName;
	private String lastName;
	private Integer status;

	public CustomUserDetails(User userEntity) {
		super(userEntity.getEmail(), userEntity.getPassword(), userEntity.getRole().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())).collect(Collectors.toList()));
		this.userId = userEntity.getUserId();
		this.firstName = userEntity.getFirstName();
		this.lastName = userEntity.getLastName();
		this.status = userEntity.getStatus();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public boolean isEnabled() {
		return this.status != 0;
	}*/

}