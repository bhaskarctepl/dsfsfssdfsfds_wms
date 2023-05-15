package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.User;

public class UserResponse {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
