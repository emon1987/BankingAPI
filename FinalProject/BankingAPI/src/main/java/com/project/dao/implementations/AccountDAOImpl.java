package com.project.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.project.dao.interfaces.AccountDAO;
import com.project.dao.interfaces.AccountStatusDAO;
import com.project.dao.interfaces.AccountTypeDAO;
import com.project.dao.interfaces.UserDAO;
import com.project.models.Account;
import com.project.models.AccountStatus;
import com.project.models.AccountType;
import com.project.services.ConnectionService;

public class AccountDAOImpl implements AccountDAO {

	Connection connection;

	public AccountDAOImpl() {
		this.connection = ConnectionService.getConnection();
	}

	@Override
	public Account createAccount(Account account) {
		try {
			UserDAO uD = new UserDAOImpl();

			if (uD.getUser(account.getUid()) != null) {
//				PreparedStatement uPs = connection.prepareStatement("Select * FROM users where user_id = ?");
//				uPs.setInt(1, account.getUid());
//				
//				ResultSet uRs = uPs.executeQuery();

				PreparedStatement ps = connection.prepareStatement("INSERT INTO account "
						+ "(balance, uid, account_status_id, account_type_id) Values(?, ?, ?, ?) returning account_id;");
				ps.setDouble(1, account.getBalance());
				ps.setInt(2, account.getUid());
				ps.setInt(3, account.getStatus().getStatusId());
				ps.setInt(4, account.getType().getTypeId());

				ps.executeQuery();
				ResultSet rs = ps.getResultSet();
				rs.next();
				account.setAccountId(rs.getInt("account_id"));

				return account;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account getAccount(int id) {
		// TODO Auto-generated method stub

		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT * FROM account WHERE account_id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setUid(rs.getInt("uid"));

				AccountStatus accountStatus = new AccountStatus();
				AccountStatusDAO acd = new AccountStatusDAOImpl();

				accountStatus.setStatusId(rs.getInt("account_status_id"));
				accountStatus.setStatusName(acd.getAccountStatusName(rs.getInt("account_status_id")));

				AccountType accountType = new AccountType();
				AccountTypeDAO atd = new AccountTypeDAOImpl();

				accountType.setTypeId(rs.getInt("account_type_id"));
				accountType.setType(atd.getAccountTypeName(rs.getInt("account_type_id")));

				account.setStatus(accountStatus);
				account.setType(accountType);

				return account;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Account updateAccount(Account account) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE account " + "SET balance = ?, uid = ?, account_status_id = ?,  account_type_id = ?"
							+ " WHERE account_id = ?");
			ps.setDouble(1, account.getBalance());
			ps.setInt(2, account.getUid());
			ps.setInt(3, account.getStatus().getStatusId());
			ps.setInt(4, account.getType().getTypeId());
			ps.setInt(5, account.getAccountId());

			ps.executeUpdate();
			return account;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getUserID(int accountID) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT uid FROM account " + "WHERE account_id = ?");
			ps.setInt(1, accountID);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("uid");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Boolean withdrawalFromAccount(Account account, double amount) {
		// TODO Auto-generated method stub
		try {
			if (account.getBalance() > amount) {

				PreparedStatement psWithdrawal = connection
						.prepareStatement("UPDATE account" + " SET balance = balance - ? WHERE account_id = ?");
				psWithdrawal.setDouble(1, amount);
				psWithdrawal.setInt(2, account.getAccountId());

				psWithdrawal.executeUpdate();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean depositToAccount(Account account, double amount) {
		// TODO Auto-generated method stub
		try {

			PreparedStatement psDepost = connection
					.prepareStatement("UPDATE account" + " SET balance = balance + ? WHERE account_id = ?");
			psDepost.setDouble(1, amount);
			psDepost.setInt(2, account.getAccountId());

			psDepost.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean transferToAccount(Account accountFrom, Account accountTo, double amount) {
		// TODO Auto-generated method stub
		try {
			if (accountFrom.getBalance() > amount) {
				PreparedStatement accountFromPs = connection
						.prepareStatement("UPDATE account" + " SET balance = ? WHERE account_id = ?");
				accountFromPs.setDouble(1, accountFrom.getBalance() - amount);
				accountFromPs.setInt(2, accountFrom.getAccountId());

				accountFromPs.executeUpdate();

				PreparedStatement accountToPs = connection
						.prepareStatement("UPDATE account" + " SET balance =  ? WHERE account_id = ?");
				accountToPs.setDouble(1, accountTo.getBalance() + amount);
				accountToPs.setInt(2, accountTo.getAccountId());

				accountToPs.executeUpdate();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void deleteAccount(Account account) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM account" + " WHERE account_id = ?;");

			ps.setInt(1, account.getAccountId());

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<Account>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM account "
					+ "LEFT JOIN account_status ON account_status.status_id = account.account_status_id "
					+ "LEFT JOIN account_type ON account_type.type_id = account.account_type_id order by account.account_id;");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setUid(rs.getInt("uid"));

				AccountStatusDAO ad = new AccountStatusDAOImpl();
				AccountTypeDAO at = new AccountTypeDAOImpl();

				account.setStatus(ad.createAccountStatus(rs.getString("status_of_account")));
				account.setType(at.createAccountType(rs.getString("type_cos")));

				accounts.add(account);

			}
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Account> getAllAccountsByStatus(int statusID) {
		List<Account> accounts = new ArrayList<Account>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM account "
					+ "LEFT JOIN account_status ON account_status.status_id = account.account_status_id "
					+ "LEFT JOIN account_type ON account_type.type_id = account.account_type_id"
					+ " WHERE account.account_status_id = ? order by account.account_id;");
			ps.setInt(1, statusID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setUid(rs.getInt("uid"));

				AccountStatusDAO ad = new AccountStatusDAOImpl();
				AccountTypeDAO at = new AccountTypeDAOImpl();

				account.setStatus(ad.createAccountStatus(rs.getString("status_of_account")));
				account.setType(at.createAccountType(rs.getString("type_cos")));

				accounts.add(account);

			}
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> getAllAccountsForUser(int userID) {
		List<Account> accounts = new ArrayList<Account>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM account "
					+ "LEFT JOIN account_status ON account_status.status_id = account.account_status_id "
					+ "LEFT JOIN account_type ON account_type.type_id = account.account_type_id"
					+ " WHERE account.uid = ? order by account.account_id;");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setUid(rs.getInt("uid"));

				AccountStatusDAO ad = new AccountStatusDAOImpl();
				AccountTypeDAO at = new AccountTypeDAOImpl();

				account.setStatus(ad.createAccountStatus(rs.getString("status_of_account")));
				account.setType(at.createAccountType(rs.getString("type_cos")));

				accounts.add(account);

			}
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}

	public List<Account> getAllAccountsForUser(int userID, int accountType) {
		List<Account> accounts = new ArrayList<Account>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM account "
					+ "LEFT JOIN account_status ON account_status.status_id = account.account_status_id "
					+ "LEFT JOIN account_type ON account_type.type_id = account.account_type_id"
					+ " WHERE account.uid = ? AND account.account_type_id = ? order by account.account_id;");
			ps.setInt(1, userID);
			ps.setInt(2, accountType);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setBalance(rs.getDouble("balance"));
				account.setUid(rs.getInt("uid"));

				AccountStatusDAO ad = new AccountStatusDAOImpl();
				AccountTypeDAO at = new AccountTypeDAOImpl();

				account.setStatus(ad.createAccountStatus(rs.getString("status_of_account")));
				account.setType(at.createAccountType(rs.getString("type_cos")));

				accounts.add(account);

			}
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return null;
	}
}