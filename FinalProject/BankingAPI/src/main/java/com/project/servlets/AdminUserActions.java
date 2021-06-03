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
import com.project.models.Messages;
import com.project.models.User;
import com.project.services.EmployeeService;
import com.project.services.UserService;

public class AdminUserActions extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();

	
	// Find all Users
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		try {
			User user = (User) session.getAttribute("currentUser");

			List<User> users = EmployeeService.findAllUsers(user.getRole().getRoleId());

			if (users != null) {
				for (User u : users) {
					pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(u));
					pw.println();
				}
				response.setStatus(200);
			} else {
				m.setMessage("The requested action is not permitted");
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
				response.setStatus(401);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Used to Update Users by the Admin or if the Current User Owns the Account
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		try {
			User user = (User) session.getAttribute("currentUser");
			JsonNode jN = mapper.readTree(request.getReader());

			int userId = jN.get("userId").asInt();
			String username = jN.get("username").asText();
			String password = jN.get("password").asText();
			String firstName = jN.get("firstName").asText();
			String lastName = jN.get("lastName").asText();
			String email = jN.get("email").asText();
			String roleName = jN.get("roleName").asText();

			User userToUpdate = UserService.updateUserByID(user.getUserId(), userId, username, password, firstName,
					lastName, email, roleName, user.getRole().getRoleId());

			if (userToUpdate != null) {
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userToUpdate));
				response.setStatus(200);
			} else {

				m.setMessage("The requested action is not permitted");
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
				response.setStatus(401);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
