package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.models.Account;
import com.project.models.Messages;
import com.project.models.User;
import com.project.services.AccountService;
import com.project.services.EmployeeService;

public class FindAccountsByUserAndType extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();
	private int status;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		String[] parts = path.split("/");
		m.setMessage("The requested access is not permitted");
		status = 401;

		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		User currentUser = (User) session.getAttribute("currentUser");
		List<Account> accounts = null;

		try {
			if (currentUser != null) {
				String queryParam = request.getQueryString();
				String[] qParts = queryParam.split("=");

				accounts = AccountService.findAccountsByUserAndType(currentUser.getUserId(), Integer.parseInt(parts[4]),
						currentUser.getRole().getRoleId(), Integer.parseInt(qParts[1]));
				if (accounts != null) {
					for (Account a : accounts) {
						pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(a));
						pw.println();
					}
					status = 200;
				}
			}

			if (currentUser == null || accounts == null) {
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
			}
			response.setStatus(status);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
