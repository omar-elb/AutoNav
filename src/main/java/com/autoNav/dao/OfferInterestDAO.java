package com.autoNav.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.autoNav.model.Offer;
import com.autoNav.util.DBConnection;

public class OfferInterestDAO {
	
	public boolean addOfferInterest(int userId, int offerId) {
        String sql = "INSERT INTO offer_interest (user_id, offer_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, userId);
            ps.setInt(2, offerId);
            
            OfferDAO offerDAO = new OfferDAO();
            Offer offer = offerDAO.getOfferById(offerId);
            if(offer.getCurrentSubscribers() == offer.getTargetSubscribers()) {
            	
            	try {
            		String sql1 = "UPDATE shuttle_offers SET interested = ? WHERE id = ?"; 
            		PreparedStatement pstmt = con.prepareStatement(sql1);
            		pstmt.setInt(1, offer.getInterested() + 1);
            		pstmt.setInt(2, offerId);
            		pstmt.executeUpdate();
            	} catch (SQLException e) {
            	    e.printStackTrace();
            	}
            };            		
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public List<Offer> getOfferInterestByUser(int userId) {
        List<Offer> offers = new ArrayList<>();
        String sql = "SELECT o.* FROM shuttle_offers o JOIN offer_interest i ON o.id = i.offer_id WHERE s.user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Offer offer = new Offer();
                    offer.setId(rs.getInt("id"));
                    offer.setCompanyId(rs.getInt("company_id"));
                    offer.setStartDate(rs.getDate("start_date"));
                    offer.setEndDate(rs.getDate("end_date"));
                    offer.setDepartureCity(rs.getString("departure_city"));
                    offer.setArrivalCity(rs.getString("arrival_city"));
                    offer.setDepartureTime(rs.getString("departure_time"));
                    offer.setArrivalTime(rs.getString("arrival_time"));
                    offer.setTargetSubscribers(rs.getInt("target_subscribers"));
                    offer.setCurrentSubscribers(rs.getInt("current_subscribers"));
                    offer.setDescription(rs.getString("description"));
                    offers.add(offer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offers;
    }

}
