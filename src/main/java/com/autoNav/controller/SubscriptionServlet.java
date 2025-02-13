package com.autoNav.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.autoNav.dao.OfferDAO;
import com.autoNav.dao.SubscriptionDAO;
import com.autoNav.model.Offer;
import com.autoNav.model.User;

@WebServlet("/subscribe")
public class SubscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String offerIdStr = request.getParameter("offerId");
        if (offerIdStr == null || offerIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Offer ID is missing.");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/offerDetails.jsp");
            rd.forward(request, response);
            return;
        }
        
        int offerId;
        try {
            offerId = Integer.parseInt(offerIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid offer ID format.");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/offerDetails.jsp");
            rd.forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        boolean success = subscriptionDAO.addSubscription(user.getId(), offerId);
        if (success) {
            OfferDAO offerDAO = new OfferDAO();
            Offer offer = offerDAO.getOfferById(offerId);
            request.setAttribute("offer", offer);
            request.setAttribute("message", "You have successfully subscribed to the offer.");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/subscription.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to subscribe. Please try again.");
            RequestDispatcher rd = request.getRequestDispatcher("/jsp/offerDetails.jsp");
            rd.forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
