package com.autoNav.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.autoNav.dao.OfferDAO;
import com.autoNav.dao.OfferInterestDAO;
import com.autoNav.dao.SubscriptionDAO;
import com.autoNav.model.Offer;
import com.autoNav.model.User;

@WebServlet("/offers")
public class OfferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession(false);

	    if (session != null && session.getAttribute("user") != null) {
	        User user = (User) session.getAttribute("user");
	        if ("COMPANY".equalsIgnoreCase(user.getRole())) {
	            response.sendRedirect(request.getContextPath() + "/companyDashboard");
	            return;
	        }
	    }

	    String departureCity = request.getParameter("departureCity");
	    String arrivalCity = request.getParameter("arrivalCity");
	    String departureTime = request.getParameter("departureTime");
	    String arrivalTime = request.getParameter("arrivalTime");
	    String priceParam = request.getParameter("price");

	    Double price = null;
	    if (priceParam != null && !priceParam.isEmpty()) {
	        try {
	            price = Double.parseDouble(priceParam);
	        } catch (NumberFormatException e) {
	            request.setAttribute("error", "Invalid price format");
	        }
	    }

	    OfferDAO offerDAO = new OfferDAO();
	    List<Offer> offers;
	    if (departureCity != null || arrivalCity != null || departureTime != null || arrivalTime != null || price != null) {
	        offers = offerDAO.searchOffers(departureCity, arrivalCity, departureTime, arrivalTime, price);
	    } else {
	        offers = offerDAO.getAllOffers(); 
	    }

	    request.setAttribute("offers", offers);

	    if (session != null && session.getAttribute("user") != null) {
	        User user = (User) session.getAttribute("user");
	        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
            List<Offer> subscribedOffers = subscriptionDAO.getSubscriptionsByUser(user.getId());
            OfferInterestDAO offerInterestDAO = new OfferInterestDAO();
            List<Offer> offersInterestedIn = offerInterestDAO.getOfferInterestByUser(user.getId());
            Set<Integer> subscribedOfferIds = new HashSet<>();
            Set<Integer> offersInterestedInIds = new HashSet<>();
            for (Offer subOffer : subscribedOffers) {
                subscribedOfferIds.add(subOffer.getId());
            }
            for (Offer intrOffer : offersInterestedIn) {
            	offersInterestedInIds.add(intrOffer.getId());
            }
            request.setAttribute("subscribedOfferIds", subscribedOfferIds);
            request.setAttribute("offersInterestedInIds", offersInterestedInIds);
	    }

	    RequestDispatcher rd = request.getRequestDispatcher("/jsp/offers.jsp");
	    rd.forward(request, response);
	}
}