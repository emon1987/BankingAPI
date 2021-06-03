package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.models.Account;
import com.project.models.Messages;
import com.project.models.User;
import com.project.services.AccountService;
import com.project.services.EmployeeService;

public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();
	private int status;

	// Submitting Accounts
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		User currentUser = (User) session.getAttribute("currentUser");
		JsonNode jN = mapper.readTree(request.getReader());

		int userID = jN.get("userId").asInt();
		double balance = jN.get("balance").asDouble();
		String statusName = jN.get("statusName").asText();
		String typeName = jN.get("typeName").asText();

		Account submittedAccount = AccountService.submitAccount(balance, userID, statusName, typeName,
				currentUser.getUserId(), currentUser.getRole().getRoleId());

		if (submittedAccount != null) {
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(submittedAccount));
			status = 201;
		} else {
			m.setMessage("The requested action is not permitted");
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
			status = 401;
		}

		response.setStatus(status);

	}

	// Find all accounts
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		try {
			User user = (User) session.getAttribute("currentUser");

			List<Account> accounts = EmployeeService.findAllAccounts(user.getRole().getRoleId());
			

			if (accounts != null) {
				for (Account a : accounts) {
					pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
					pw.println();
				}
				status = 200;
			} else {
				m.setMessage("The requested action is not permitted");
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
				status = 401;
			}
			response.setStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Updating Accounts
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		User currentUser = (User) session.getAttribute("currentUser");
		JsonNode jN = mapper.readTree(request.getReader());

		int accountID = jN.get("accountId").asInt();
		int userID = jN.get("userId").asInt();
		double balance = jN.get("balance").asDouble();
		String statusName = jN.get("statusName").asText();
		String typeName = jN.get("typeName").asText();

		Account updatedAccount = EmployeeService.updateAccount(accountID, balance, userID, statusName, typeName,
				currentUser.getRole().getRoleId());

		if (updatedAccount != null) {
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(updatedAccount));
			status = 200;
		} else {
			m.setMessage("The requested action is not permitted");
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
			status = 401;
		}

		response.setStatus(status);

	}
}