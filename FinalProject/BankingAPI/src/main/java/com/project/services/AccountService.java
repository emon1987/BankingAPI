package com.project.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.project.dao.implementations.AccountDAOImpl;
import com.project.dao.implementations.AccountStatusDAOImpl;
import com.project.dao.implementations.AccountTypeDAOImpl;
import com.project.dao.implementations.RoleDAOImpl;
import com.project.dao.implementations.UserDAOImpl;
import com.project.dao.interfaces.AccountDAO;
import com.project.dao.interfaces.AccountStatusDAO;
import com.project.dao.interfaces.AccountTypeDAO;
import com.project.dao.interfaces.RoleDAO;
import com.project.dao.interfaces.UserDAO;
import com.project.dao.interfaces.RoleDAO.roles;
import com.project.models.Account;
import com.project.models.User;

public class AccountService {

	public static Account submitAccount(double balance, int userID, String statusName, String typeName, int currUserID,
			int currentUserRole) {
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal() || userID == currUserID) {
			Account account = new Account();
			UserDAO ud = new UserDAOImpl();

			AccountStatusDAO statusDAO = new AccountStatusDAOImpl();
			AccountTypeDAO typeDAO = new AccountTypeDAOImpl();

			account.setUid(userID);
			account.setBalance(balance);
			account.setStatus(statusDAO.createAccountStatus(statusName));
			account.setType(typeDAO.createAccountType(typeName));

			AccountDAO ad = new AccountDAOImpl();

			return ad.createAccount(account);
		}
		return null;
	}

	public static Account findAccountByID(int currentUserID, int accountID, int currentUserRole) {
		AccountDAO ad = new AccountDAOImpl();

		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal() || currentUserID == ad.getUserID(accountID)) {

			return ad.getAccount(accountID);
		}
		return null;
	}

	public static List<Account> findAccountsByUser(int currentUserID, int userID, int currentUserRole) {
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal() || userID == currentUserID) {

			AccountDAO ad = new AccountDAOImpl();

			return ad.getAllAccountsForUser(userID);
		}
		return null;
	}
	
	public static List<Account> findAccountsByUserAndType(int currentUserID, int userID, int currentUserRole, int accountType) {
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserRole == roles.valueOf("Employee").ordinal() || userID == currentUserID) {

			AccountDAO ad = new AccountDAOImpl();

			return ad.getAllAccountsForUser(userID, accountType);
		}
		return null;
	}
}
