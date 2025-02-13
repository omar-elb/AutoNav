package com.autoNav.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.autoNav.dao.OfferDAO;
import com.autoNav.model.User;


@WebServlet("/deleteOffer")
public class DeleteOfferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!"COMPANY".equalsIgnoreCase(user.getRole())) {
            
        	response.sendRedirect(request.getContextPath() + "/offers");
            return;
        }
        
        
        String offerIdStr = request.getParameter("offerId");
        if (offerIdStr != null && !offerIdStr.trim().isEmpty()) {
            try {
                int offerId = Integer.parseInt(offerIdStr);

                OfferDAO offerDAO = new OfferDAO();
                boolean deleted = offerDAO.deleteOffer(offerId);
                if (deleted) {
                    
                	session.setAttribute("message", "Offer deleted successfully.");
                } else {
                    session.setAttribute("errorMessage", "Failed to delete offer.");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid offer ID format.");
            }
        } else {
            session.setAttribute("errorMessage", "Offer ID is missing.");
        }
        
        response.sendRedirect(request.getContextPath() + "/companyDashboard");
    }

}
