package com.autoNav.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.autoNav.dao.UserDAO;
import com.autoNav.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If user is already logged in, redirect based on role.
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            if ("COMPANY".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/companyDashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/offers");
            }
            return;
        }
        // Otherwise, forward to login page.
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            if ("COMPANY".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/companyDashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/offers");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
            rd.forward(request, response);
        }
    }
}
