package com.project.dao.implementations;

import java.sql.Connection;
import com.project.dao.interfaces.RoleDAO;
import com.project.models.Role;
import com.project.services.ConnectionService;

public class RoleDAOImpl implements RoleDAO {

	Connection connection;

	
	public RoleDAOImpl() {
		this.connection = ConnectionService.getConnection();
	}
	@Override
	public Role getRole(String roleName) {
		Role role = new Role();
		for (int i = 1; i < roles.values().length; i++) {
			if (roles.values()[i].toString().equals(roleName)) {
				role.setRoleId(roles.valueOf(roleName).ordinal());
				role.setRole(roleName);
				return role;
			}
		}
		return null;
	}
	
	@Override
	public int getRoleId(String role) {
		return roles.valueOf(role).ordinal();

	}

	@Override
	public String getRoleName(int id) {
		// TODO Auto-generated method stub
		return roles.values()[id].toString();
		
	}

}
