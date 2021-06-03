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
import com.project.models.Messages;
import com.project.models.User;
import com.project.services.EmployeeService;

public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();

		try {
			User user = (User) session.getAttribute("currentUser");
			JsonNode jN = mapper.readTree(request.getReader());

			String username = jN.get("username").asText();
			String password = jN.get("password").asText();
			String firstName = jN.get("firstName").asText();
			String lastName = jN.get("lastName").asText();
			String email = jN.get("email").asText();
			String roleName = jN.get("roleName").asText();

			User userToRegister = EmployeeService.registerUser(username, password, firstName, lastName, email, roleName,
					user.getRole().getRoleId());

			if (userToRegister != null) {

				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userToRegister));
				response.setStatus(201);

			} else {
				m.setMessage("Invalid Credentials");
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
				response.setStatus(400);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
