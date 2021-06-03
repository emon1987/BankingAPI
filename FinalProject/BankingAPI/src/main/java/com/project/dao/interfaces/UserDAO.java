package com.project.dao.interfaces;

import java.util.List;

import com.project.models.User;

public interface UserDAO {

	public User createUser(User user);

	public User getUser(int id);

	public User getUser(String username);

	public int getUserRole(int userID);

	public User updateUser(User user);
	
	public void deleteUser(int id);

	public void deleteUser(String username);

	public List<User> getAllUsers();
}
