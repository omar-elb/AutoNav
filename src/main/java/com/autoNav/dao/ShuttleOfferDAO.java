package com.autoNav.dao;

import java.sql.*;
import java.util.*;

import com.autoNav.model.ShuttleOffer;
import com.autoNav.util.DBConnection;

public class ShuttleOfferDAO {
	
	public List<ShuttleOffer> getAllOffers() {
        List<ShuttleOffer> offers = new ArrayList<>();
        String sql = "SELECT * FROM shuttle_offers";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
        	while (rs.next()) {
                ShuttleOffer offer = new ShuttleOffer();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offers;
	}
	
	public ShuttleOffer getOfferById(int id) {
        ShuttleOffer offer = null;
        String sql = "SELECT * FROM shuttle_offers WHERE id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    offer = new ShuttleOffer();
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }
	
	public boolean createOffer(ShuttleOffer offer) {
		String sql = "INSERT INTO shuttle_offers (company_id, start_date, end_date, departure_city, arrival_city, departure_time, arrival_time, target_subscribers, current_subscribers, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, offer.getCompanyId());
            ps.setDate(2, new java.sql.Date(offer.getStartDate().getTime()));
            ps.setDate(3, new java.sql.Date(offer.getEndDate().getTime()));
            ps.setString(4, offer.getDepartureCity());
            ps.setString(5, offer.getArrivalCity());
            ps.setString(6, offer.getDepartureTime());
            ps.setString(7, offer.getArrivalTime());
            ps.setInt(8, offer.getTargetSubscribers());
            ps.setInt(9, offer.getCurrentSubscribers());
            ps.setString(10, offer.getDescription());
            
            return ps.executeUpdate() > 0;
		}catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

	}
	
	public boolean updateOffer(ShuttleOffer offer) {
		String sql = "UPDATE shuttle_offers SET company_id = ?, start_date = ?, end_date = ?, departure_city = ?, arrival_city = ?, departure_time = ?, arrival_time = ?, target_subscribers = ?, current_subscribers = ?, description = ? WHERE id = ?";
		
		try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	             
			ps.setInt(1, offer.getCompanyId());
			ps.setDate(2, new java.sql.Date(offer.getStartDate().getTime()));
			ps.setDate(3, new java.sql.Date(offer.getEndDate().getTime()));
			ps.setString(4, offer.getDepartureCity());
			ps.setString(5, offer.getArrivalCity());
			ps.setString(6, offer.getDepartureTime());
			ps.setString(7, offer.getArrivalTime());
			ps.setInt(8, offer.getTargetSubscribers());
			ps.setInt(9, offer.getCurrentSubscribers());
			ps.setString(10, offer.getDescription());
			ps.setInt(11, offer.getId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteOffer(int offerId) {
		String sql = "DELETE FROM shuttle_offers WHERE id = ?";
		
		try (Connection con = DBConnection.getConnection(); 
			 PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, offerId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
