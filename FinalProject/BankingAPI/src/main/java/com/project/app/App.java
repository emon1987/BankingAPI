package com.project.app;


import java.util.*;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.project.dao.implementations.AccountDAOImpl;
import com.project.dao.implementations.AccountStatusDAOImpl;
import com.project.dao.implementations.AccountTypeDAOImpl;
import com.project.dao.implementations.UserDAOImpl;
import com.project.dao.interfaces.AccountDAO;
import com.project.dao.interfaces.AccountStatusDAO;
import com.project.dao.interfaces.AccountTypeDAO;
import com.project.dao.interfaces.UserDAO;
import com.project.models.Account;

public class App {
	public static void main (String []args) {
		String plaintext = "12345";
		String hashed	=	"$2a$10$Fmw/0yjFGz2Bw0iZWJ7McOjKgbwLjxzN4ldA/Jk9B.2zlIoCUVG9e";
		//String hashed2 = "$2a$10$p4Hde.G0QagAov49.WSL/OOKSM5r4WEw6.e6QzNJJjfejpKfH5LbK";
		if (BCrypt.checkpw(plaintext, hashed)) {
		System.out.println("This is it");	
		}
//		
//		if (BCrypt.checkpw(plaintext, hashed2)) {
//			System.out.println("They both work");	
//			}
		
		//User Tester 
//		UserDAO userDao = new UserDAOImpl();
//		User user = new User();
//		user.setEmail("Princess@revature.com");
//		user.setFirstName("Princess");
//		user.setLastName("Peach");
//		user.setPassword("toad");
//		user.setUsername("PrincessToadstool");
//		
//		RoleDAO roleDao = new RoleDAOImpl();
//		
//		
//		user.setRole(roleDao.getRole("Premium"));
//		
//		userDao.createUser(user);
//		
//		int [] users = {2, 10, 11, 12, 13};
//		String [] aTypes = {"Checking", "Savings"};
//		
//		String[] aStatus = {"Pending", "Open", "Closed", "Denied"};
//		Random random = new Random();
//		// Account Tester
//		int count = 0;
//		while (count < 30) {
//			int randUser = random.nextInt(5);
//			
//			int randAS = random.nextInt(4);
//			
//			int randAT = random.nextInt(2);
//			double randBalance = random.nextDouble() * (5000) + 100.76;
//			
//		AccountDAO accountDao = new AccountDAOImpl();
//		Account account = new Account();
//		AccountStatusDAO statusDao = new AccountStatusDAOImpl();
//		UserDAO ud = new UserDAOImpl();
//		// 2 10, 11, 12, 13
//		
//		AccountTypeDAO typeDAO = new AccountTypeDAOImpl();
//		
//		//('Pending'), ('Open'), ('Closed'), ('Denied');
//		
//		account.setBalance(randBalance);
//		account.setUid(users[randUser]);
//		account.setStatus(statusDao.createAccountStatus(aStatus[randAS].toString()));
//		account.setUser(ud.getUser(users[randUser]));
//		account.setType(typeDAO.createAccountType(aTypes[randAT].toString()));
//		
//		// Testing Create Account
//		
//		accountDao.createAccount(account);
//		count++;
//		}
//		
		
		
		
	}

}
