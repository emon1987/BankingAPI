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
import com.project.services.UserService;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw = response.getWriter();
		
		try {
			JsonNode jN = mapper.readTree(request.getReader());
			String username = jN.get("username").asText();
			String password = jN.get("password").asText();
			
			
			User user = UserService.loginService(username, password);
			if (user != null) {
				session.setAttribute("currentUser", user);
				response.setStatus(200);
				try {
					pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				response.setStatus(400);
				Messages m = new Messages();
				m.setMessage("Invalid Credentials");
				pw.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
