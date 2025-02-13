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

import com.autoNav.dao.OfferDAO;
import com.autoNav.model.Offer;
import com.autoNav.model.User;


@WebServlet("/companyDashboard")
public class CompanyDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check that only company users access this dashboard.
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!"COMPANY".equalsIgnoreCase(user.getRole())) {
            // Non-company users should not access company dashboard.
            response.sendRedirect(request.getContextPath() + "/offers");
            return;
        }
        
        // Retrieve offers created by the company.
        OfferDAO offerDAO = new OfferDAO();
        List<Offer> companyOffers = offerDAO.getOffersByCompanyId(user.getId());
        request.setAttribute("companyOffers", companyOffers);
        
        RequestDispatcher rd = request.getRequestDispatcher("/jsp/companyDashboard.jsp");
        rd.forward(request, response);
    }

}
