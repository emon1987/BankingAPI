package com.project.dao.interfaces;

import com.project.models.Role;

public interface RoleDAO {
	
	
	public enum roles {
		Undefined, Admin, Employee, Standard , Premium	
	}
	
	public Role getRole(String roleName);
	
	public int getRoleId(String role);
	
	public String getRoleName(int id);
	
	

}
