package com.project.models;

public class AccountStatus {
	
	
	
	private int statusId; // primary key
	private String status; // not null, unique
	
	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return status;
	}

	public void setStatusName(String status) {
		this.status = status;
	}

//	public int getStatusIdFromName (String statusName) {
//		statusNames r = statusNames.valueOf(statusName);
//		return r.ordinal();
//	}
}
