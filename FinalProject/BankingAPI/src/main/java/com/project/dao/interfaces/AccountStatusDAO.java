package com.project.dao.interfaces;

import com.project.models.Account;
import com.project.models.AccountStatus;

public interface AccountStatusDAO {
	
	public enum statusNames {
		Undefined, Pending, Open, Closed, Denied
	}
	
	public AccountStatus createAccountStatus (String statusName);
	
	public int getAccountStatusId(String status);
	
	public String getAccountStatusName(int id);
	
	public void updateAccountStatus (Account account, String status);
	
	
	
}
