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

public class LogoutServlet extends HttpServlet {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private Messages m = new Messages();
	private int status;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// HttpSession session = request.getSession();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		try {
			User user = (User) session.getAttribute("currentUser");

			if (user != null) {
				System.out.println("1: " + user.getUsername());

				m.setMessage("You have successfully logged out" + user.getUsername());

				session.invalidate();
				status = 200;

			} else {
				m.setMessage("There was no user logged into the session");

				response.setStatus(400);
				status = 400;
			}
			pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
			response.setStatus(status);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}