package com.project.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.models.Messages;
import com.project.models.User;
import com.project.services.UserService;

public class FindUserByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();
	private int status;

	// Handles finding users by user ID
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getRequestURI();
		String[] parts = path.split("/");
		m.setMessage("The requested access is not permitted");
		status = 401;

		System.out.println("User requested " + Integer.parseInt(parts[3]));
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		User currentUser = (User) session.getAttribute("currentUser");

		if (currentUser != null) {
			User userRequested = UserService.findUserByID(currentUser.getUserId(), Integer.parseInt(parts[3]),
					currentUser.getRole().getRoleId());

			if (userRequested != null) {
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRequested));
				status = 200;
			}
		} else {
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
		}
		response.setStatus(status);

	}

}
