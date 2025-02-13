package com.autoNav.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.autoNav.dao.UserDAO;
import com.autoNav.model.User;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If logged in, redirect to proper home page.
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
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Same registration process.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        UserDAO userDAO = new UserDAO();
        if (userDAO.addUser(user)) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/register.jsp");
            rd.forward(request, response);
        }
    }
}