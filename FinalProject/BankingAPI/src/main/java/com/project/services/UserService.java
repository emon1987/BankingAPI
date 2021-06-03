package com.project.services;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.project.dao.implementations.RoleDAOImpl;
import com.project.dao.implementations.UserDAOImpl;
import com.project.dao.interfaces.RoleDAO;
import com.project.dao.interfaces.UserDAO;
import com.project.dao.interfaces.RoleDAO.roles;
import com.project.models.User;

public class UserService {
	// This will handle Login and registering
	public static User loginService(String username, String password) {
		UserDAO ud = new UserDAOImpl();
		User user = new User();

		user = ud.getUser(username);
		if (user != null) {
			if (BCrypt.checkpw(password, user.getPassword())) {
				// if (user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;

	}
	
	public static User findUserByID(int currentUserID, int requestedUserID, int currentUserRole) {
		UserDAO ud = new UserDAOImpl();
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal()
				|| requestedUserID == currentUserID) {
			return ud.getUser(requestedUserID);
		}
		return null;
	}

	public static User updateUserByID(int currentUserID, int userToUpdate, String username, String password,
			String firstName, String lastName, String email, String roleName,  int currentUserRole) {
		UserDAO ud = new UserDAOImpl();
		if (currentUserRole == roles.valueOf("Admin").ordinal() || userToUpdate == currentUserID) {
			User user = new User();
			user.setUserId(userToUpdate);
			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);

			RoleDAO rd = new RoleDAOImpl();
			user.setRole(rd.getRole(roleName));

			return ud.updateUser(user);
		}
		return null;
	}
}
