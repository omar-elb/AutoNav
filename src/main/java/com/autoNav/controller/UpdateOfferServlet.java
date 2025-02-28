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
import com.autoNav.model.Offer;
import com.autoNav.model.User;


@WebServlet("/updateOffer")
public class UpdateOfferServlet extends HttpServlet {
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
                Offer offer = offerDAO.getOfferById(offerId);
                request.setAttribute("offer", offer);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid offer ID format.");
            }
        } else {
            request.setAttribute("errorMessage", "Offer ID is missing.");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/updateOffer.jsp");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        
        try {
            int offerId = Integer.parseInt(request.getParameter("offerId"));
            OfferDAO offerDAO = new OfferDAO();
            Offer offer = offerDAO.getOfferById(offerId);
            if (offer == null) {
                request.setAttribute("errorMessage", "Offer not found.");
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/updateOffer.jsp");
                rd.forward(request, response);
                return;
            }
            
            offer.setCompanyId(user.getId());
            offer.setDepartureCity(request.getParameter("departureCity"));
            offer.setArrivalCity(request.getParameter("arrivalCity"));
            offer.setDepartureTime(request.getParameter("departureTime"));
            offer.setArrivalTime(request.getParameter("arrivalTime"));
            offer.setStartDate(java.sql.Date.valueOf(request.getParameter("startDate")));
            offer.setEndDate(java.sql.Date.valueOf(request.getParameter("endDate")));
            offer.setTargetSubscribers(Integer.parseInt(request.getParameter("targetSubscribers")));
            offer.setPrice(Double.parseDouble(request.getParameter("price")));
            offer.setDescription(request.getParameter("description"));
            
            boolean updated = offerDAO.updateOffer(offer);
            if (updated) {
                session.setAttribute("message", "Offer updated successfully.");
            } else {
                session.setAttribute("errorMessage", "Failed to update offer.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error processing update: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/companyDashboard");
    }

}
