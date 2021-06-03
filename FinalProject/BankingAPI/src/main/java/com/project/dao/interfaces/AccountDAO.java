package com.project.dao.interfaces;

import java.util.List;

import com.project.models.Account;

public interface AccountDAO {
	public Account createAccount (Account account);

	public Account getAccount(int id);

	public Account updateAccount(Account account);
	
	public Boolean withdrawalFromAccount(Account account, double amount);
	
	public Boolean depositToAccount(Account account, double amount);
	
	public Boolean transferToAccount(Account accountFrom, Account accountTo, double amount);
	
	public int getUserID (int accountID);

	public void deleteAccount(Account account);

	public List<Account> getAllAccounts();
	
	public List<Account> getAllAccountsByStatus(int statusID);
	
	public List<Account> getAllAccountsForUser(int userID);
	
	public List<Account> getAllAccountsForUser(int userID, int accountType);

}
