package com.project.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.project.dao.interfaces.RoleDAO;
import com.project.dao.interfaces.UserDAO;
import com.project.models.User;
import com.project.services.ConnectionService;

public class UserDAOImpl implements UserDAO {

	Connection connection;

	public UserDAOImpl() {
		this.connection = ConnectionService.getConnection();
	}
	
	
	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO users "
					+ "(username, pword, first_name, last_name, email, role_for_this_user) "
					+ "Values (?, ?, ?, ?, ?, ?) returning user_id;");
			ps.setString(1, user.getUsername());
			ps.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole().getRoleId());
			
			ps.executeQuery();
			
			ResultSet rs = ps.getResultSet();
			rs.next();
			
			user.setUserId(rs.getInt("user_id"));
			return user;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * from users WHERE user_id = ?");
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("pword"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				
			
				RoleDAO rd = new RoleDAOImpl();
				
				user.setRole(rd.getRole(rd.getRoleName(rs.getInt("role_for_this_user"))));
				
				return user;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public User getUser(String username) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * from users WHERE username = ?");
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("pword"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				
				
				RoleDAO rd = new RoleDAOImpl();
				
				user.setRole(rd.getRole(rd.getRoleName(rs.getInt("role_for_this_user"))));
				
				return user;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public int getUserRole(int userID) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT role_for_this_user from users WHERE user_id = ?");
			ps.setInt(1, userID);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("role_for_this_user");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE users "
					+ "SET username = ?, pword = ?, first_name = ?, "
					+ "last_name = ?, email = ?, role_for_this_user = ? "
					+ "WHERE user_id = ?;");
			ps.setString(1, user.getUsername());
			ps.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRole().getRoleId());
			ps.setInt(7, user.getUserId());
			
			ps.executeUpdate();
			
			return user;
		} catch (Exception e) {
			
		}
		return null;

	}

	@Override
	public void deleteUser(int id) {
		try {
			//First have to delete all accounts for that user
			PreparedStatement accountPs = connection.prepareStatement("DELETE FROM account "
					+ "WHERE uid = ?");
			accountPs.setInt(1, id);
			
			accountPs.executeUpdate();
			
			PreparedStatement ps = connection.prepareStatement("DELETE FROM users" 
					+ " WHERE user_id = ?");
			ps.setInt(1, id);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		try {
			// Find All accounts Accociated with that Username
			PreparedStatement findUID = connection.prepareStatement("SELECET user_id FROM "
					+ "users WHERE username = ?");
			findUID.setString(1, username);
			
			ResultSet rs = findUID.executeQuery();
			if (rs.next()) {
				PreparedStatement accountPs = connection.prepareStatement("DELETE FROM account "
						+ "WHERE uid = ?");
				accountPs.setInt(1, rs.getInt("user_id"));
			}
			
			PreparedStatement ps = connection.prepareStatement("DELETE FROM users" 
					+ " WHERE username = ?");
			ps.setString(1, username);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users "
					+ "LEFT JOIN roles on users.role_for_this_user = roles.role_id "
					+ "order by user_id");
			ResultSet rs = ps.executeQuery();
			
		
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("pword"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				
				RoleDAO rd = new RoleDAOImpl();
				
				
				user.setRole(rd.getRole(rd.getRoleName(rs.getInt("role_for_this_user"))));
				
				users.add(user);
			}
			return users;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
