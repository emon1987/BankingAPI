package com.project.dao.interfaces;

import com.project.models.AccountType;

public interface AccountTypeDAO {
	
	public enum types {
		Undefined, Checking, Savings
	}
	
	public AccountType createAccountType(String typeName);
	
	public int getAccountTypeId(String type);
	
	public String getAccountTypeName(int id);
	
	
}
