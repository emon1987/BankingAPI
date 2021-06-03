package com.project.dao.implementations;

import java.sql.Connection;

import com.project.dao.interfaces.AccountTypeDAO;
import com.project.dao.interfaces.AccountStatusDAO.statusNames;
import com.project.models.AccountType;
import com.project.services.ConnectionService;

public class AccountTypeDAOImpl implements AccountTypeDAO {
	
	Connection connection;
	
	public AccountTypeDAOImpl() {
		this.connection = ConnectionService.getConnection();
	}
	
	@Override
	public AccountType createAccountType(String typeName) {
		AccountType at = new AccountType();
		for (int i = 1; i < types.values().length; i++) {
			if (types.values()[i].toString().equals(typeName)) {
				at.setTypeId(types.valueOf(typeName).ordinal());
				at.setType(typeName);
				return at;
			}
		}
		return null;
	}

	@Override
	public int getAccountTypeId(String type) {
			return types.valueOf(type).ordinal();
	}

	@Override
	public String getAccountTypeName(int id) {
		// TODO Auto-generated method stub
		return types.values()[id].toString();
	}

}
