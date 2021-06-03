package com.project.services;

import com.project.dao.implementations.AccountDAOImpl;
import com.project.dao.interfaces.AccountDAO;
import com.project.dao.interfaces.RoleDAO.roles;
import com.project.models.Account;

public class TransactionServices {

	// Deposit, Transfers and Withdrawals
	public static Boolean withdawalService(int accountId, double amount, int currentUserID, int currentUserRole) {
		AccountDAO ad = new AccountDAOImpl();
		Account account = new Account();
		account = ad.getAccount(accountId);

		if (currentUserRole == roles.valueOf("Admin").ordinal() || currentUserID == account.getUid()) {
			return ad.withdrawalFromAccount(account, amount);
		}

		return false;
	}

	public static Boolean depositService(int accountId, double amount, int currentUserID, int currentUserRole) {
		AccountDAO ad = new AccountDAOImpl();
		Account account = new Account();
		account = ad.getAccount(accountId);
		
		if (currentUserRole == roles.valueOf("Admin").ordinal() || currentUserID == account.getUid()) {
			
			return ad.depositToAccount(account, amount);
			
		}
		return false;
	}

	public static Boolean transferService(int sourceAccountId, int targetAccountId, double amount, int currentUserID,
			int currentUserRole) {
		AccountDAO ad = new AccountDAOImpl();
		Account accountFrom = new Account();
		Account accountTo = new Account();
		accountFrom = ad.getAccount(sourceAccountId);
		accountTo = ad.getAccount(targetAccountId);
		
		if (currentUserRole == roles.valueOf("Admin").ordinal()
				|| currentUserID == accountFrom.getUid()) {
			return  ad.transferToAccount(accountFrom, accountTo, amount);
		}
		return false;
	}
}
