package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.project.services.TransactionServices;

public class AccountByID_TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();
	private int status;

	// Handles finding accounts by account ID
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		String[] parts = path.split("/");
		m.setMessage("The requested access is not permitted");
		status = 401;

		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		User currentUser = (User) session.getAttribute("currentUser");

		if (currentUser != null) {
			Account accountRequested = AccountService.findAccountByID(currentUser.getUserId(),
					Integer.parseInt(parts[3]), currentUser.getRole().getRoleId());
			if (accountRequested != null) {
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(accountRequested));
				status = 200;
			}
		} else {
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
		}
		response.setStatus(status);

	}

	// Deposit Withdraw and Transfers handled here
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		String[] parts = path.split("/");
		m.setMessage("The requested access is not permitted");
		status = 401;

		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		JsonNode jN = mapper.readTree(request.getReader());
		User user = (User) session.getAttribute("currentUser");

		switch (parts[3]) {
		case "deposit":
			System.out.println("In Deposit");
			if (TransactionServices.depositService(jN.get("accountId").asInt(), jN.get("amount").asDouble(),
					user.getUserId(), user.getRole().getRoleId())) {
				m.setMessage("$" + jN.get("amount").asDouble() + " has been deposited to Account #"
						+ jN.get("accountId").asInt());
				status = 200;
			}

			break;
		case "withdraw":
			if (TransactionServices.withdawalService(jN.get("accountId").asInt(), jN.get("amount").asDouble(),
					user.getUserId(), user.getRole().getRoleId())) {
				m.setMessage("$" + jN.get("amount").asDouble() + " has been withdrawn from Account #"
						+ jN.get("accountId").asInt());

				status = 200;
			}

			break;
		case "transfer":

			if (TransactionServices.transferService(jN.get("sourceAccountId").asInt(),
					jN.get("targetAccountId").asInt(), jN.get("amount").asDouble(), user.getUserId(),
					user.getRole().getRoleId())) {
				m.setMessage("$" + jN.get("amount").asDouble() + " has been transferred from Account #"
						+ jN.get("sourceAccountId").asInt() + "  to Account #" + jN.get("targetAccountId").asInt());
				status = 200;
			}
			break;
		}
		pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
		response.setStatus(status);
	}

}
