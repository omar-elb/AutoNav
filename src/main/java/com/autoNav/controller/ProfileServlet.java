package com.autoNav.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.autoNav.dao.SubscriptionDAO;
import com.autoNav.dao.UserDAO;
import com.autoNav.model.Offer;
import com.autoNav.model.User;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        List<Offer> subscriptions = subscriptionDAO.getSubscriptionsByUser(user.getId());
        request.setAttribute("subscriptions", subscriptions);
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/profile.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        String newUsername = request.getParameter("username");
        String newPassword = request.getParameter("password");
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        UserDAO userDAO = new UserDAO();
        boolean updated = userDAO.updateUser(user);
        if (updated) {
            request.setAttribute("message", "Profile updated successfully.");
            session.setAttribute("user", user);
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
        }
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        List<Offer> subscriptions = subscriptionDAO.getSubscriptionsByUser(user.getId());
        request.setAttribute("subscriptions", subscriptions);
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/profile.jsp");
        rd.forward(request, response);
    }
}