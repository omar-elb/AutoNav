package com.autoNav.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.autoNav.dao.OfferInterestDAO;
import com.autoNav.model.User;

@WebServlet("/offerInterest")
public class OfferInterestServlet extends HttpServlet {
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
        
        OfferInterestDAO offerInterestDAO = new OfferInterestDAO();
        boolean success = offerInterestDAO.addOfferInterest(user.getId(), offerId);
        if (success) {
        	OfferDetailsServlet offerDetailsServlet = new OfferDetailsServlet();
        	offerDetailsServlet.doGet(request, response);
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
