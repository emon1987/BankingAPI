package com.project.services;

import java.util.List;

import com.project.dao.implementations.AccountDAOImpl;
import com.project.dao.implementations.AccountStatusDAOImpl;
import com.project.dao.implementations.AccountTypeDAOImpl;
import com.project.dao.implementations.RoleDAOImpl;
import com.project.dao.implementations.UserDAOImpl;
import com.project.dao.interfaces.AccountDAO;
import com.project.dao.interfaces.AccountStatusDAO;
import com.project.dao.interfaces.AccountTypeDAO;
import com.project.dao.interfaces.RoleDAO;
import com.project.dao.interfaces.RoleDAO.roles;
import com.project.dao.interfaces.UserDAO;
import com.project.models.Account;
import com.project.models.User;

public class EmployeeService {
	// This is where admin and employee services will be handles
	
	public static User registerUser(String username, String password, String firstName,
			String lastName, String email, String roleName, int currentUserRole) {
		UserDAO ud = new UserDAOImpl();
		if (currentUserRole == roles.valueOf("Admin").ordinal()) {
			User user = new User();
			RoleDAO rd = new RoleDAOImpl();
			
			user.setUsername(username);
			user.setPassword(password);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setRole(rd.getRole(roleName));
			
			return ud.createUser(user);
		}
		return null;
	}
	

	public static Account updateAccount(int accountId, double balance, int userID, String statusName, String typeName,
			int currentUserRole) {
		if (currentUserRole == roles.valueOf("Admin").ordinal()) {
			Account account = new Account();
			
			AccountStatusDAO statusDAO = new AccountStatusDAOImpl();
			AccountTypeDAO typeDAO = new AccountTypeDAOImpl();

			account.setAccountId(accountId);
			account.setUid(userID);
			account.setBalance(balance);
			account.setStatus(statusDAO.createAccountStatus(statusName));
			account.setType( typeDAO.createAccountType(typeName));

			AccountDAO ad = new AccountDAOImpl();
			
			return ad.updateAccount(account);
		}
		return null;
	}

	public static List<User> findAllUsers(int currentUserRole) {		
		UserDAO ud = new UserDAOImpl();
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal()) {
			return ud.getAllUsers();
		}
		return null;
	}
	

	public static List<Account> findAllAccounts( int currentUserRole) {
		if ( currentUserRole == roles.valueOf("Admin").ordinal()
				||  currentUserRole == roles.valueOf("Employee").ordinal()) {
			AccountDAO ad = new AccountDAOImpl();
			return ad.getAllAccounts();
		}
		return null;
	}
	
	
	// Find accounts by status
	// Update DAO
	
	public static List<Account> findAccountsByStatus( int currentUserRole, int statusID) {
		
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal()) {
			AccountDAO ad = new AccountDAOImpl();
			return ad.getAllAccountsByStatus(statusID);
		}
		return null;
	}
}
