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


@WebServlet("/addOffer")
public class AddOfferServlet extends HttpServlet {
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
        
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/addOffer.jsp");
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
            OfferDAO offerDAO = new OfferDAO();
            Offer offer = new Offer();
            
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
            
            boolean added = offerDAO.createOffer(offer);
            if (added) {
                session.setAttribute("message", "Offer added successfully.");
            } else {
                session.setAttribute("errorMessage", "Failed to add offer.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error processing add: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/companyDashboard");
    }
}
