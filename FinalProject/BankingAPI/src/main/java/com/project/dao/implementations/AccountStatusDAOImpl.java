package com.project.dao.implementations;

import java.sql.Connection;

import com.project.dao.interfaces.AccountStatusDAO;
import com.project.models.Account;
import com.project.models.AccountStatus;
import com.project.services.ConnectionService;

public class AccountStatusDAOImpl implements AccountStatusDAO {
	
	Connection connection;
	
	public AccountStatusDAOImpl() {
		this.connection = ConnectionService.getConnection();
	}

	@Override
	public AccountStatus createAccountStatus (String statusName) {
		AccountStatus ac = new AccountStatus();
		for (int i = 1; i < statusNames.values().length; i++) {
			if (statusNames.values()[i].toString().equals(statusName)) {
				ac.setStatusId(statusNames.valueOf(statusName).ordinal());
				ac.setStatusName(statusName);
				return ac;
			}
		}
		return null;
	}
	

	@Override
	public int getAccountStatusId(String status) {
			return statusNames.valueOf(status).ordinal();
	}
	
	@Override
	public String getAccountStatusName(int id) {
		return statusNames.values()[id].toString();
	}

	@Override
	public void updateAccountStatus(Account account, String status) {
		// TODO Auto-generated method stub
		
		
	}

}
