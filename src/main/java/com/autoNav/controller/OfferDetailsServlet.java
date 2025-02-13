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
import com.autoNav.dao.SubscriptionDAO;
import com.autoNav.model.Offer;
import com.autoNav.model.User;

@WebServlet("/offerDetails")
public class OfferDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String offerIdStr = request.getParameter("offerId");
        if (offerIdStr != null && !offerIdStr.trim().isEmpty()) {
            try {
                int offerId = Integer.parseInt(offerIdStr);
                OfferDAO offerDAO = new OfferDAO();
                Offer offer = offerDAO.getOfferById(offerId);
                request.setAttribute("offer", offer);
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("user") != null) {
                    User user = (User) session.getAttribute("user");
                    SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
                    List<Offer> subscribedOffers = subscriptionDAO.getSubscriptionsByUser(user.getId());
                    Set<Integer> subscribedOfferIds = new HashSet<>();
                    for (Offer subOffer : subscribedOffers) {
                        subscribedOfferIds.add(subOffer.getId());
                    }
                    request.setAttribute("subscribedOfferIds", subscribedOfferIds);
                }               
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid offer ID format.");
            }
        } else {
            request.setAttribute("errorMessage", "Offer ID is missing.");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/offerDetails.jsp");
        rd.forward(request, response);
    }
}
