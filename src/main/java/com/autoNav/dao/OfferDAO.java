package com.autoNav.dao;

import java.sql.*;
import java.util.*;

import com.autoNav.model.Offer;
import com.autoNav.util.DBConnection;

public class OfferDAO {
	
	public List<Offer> getAllOffers() {
        List<Offer> offers = new ArrayList<>();
        String sql = "SELECT * FROM shuttle_offers";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
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
                offer.setPrice(rs.getDouble("price"));
                offers.add(offer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offers;
	}
	
	public Offer getOfferById(int id) {
        Offer offer = null;
        String sql = "SELECT * FROM shuttle_offers WHERE id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    offer = new Offer();
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
                    offer.setPrice(rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }
	
	public List<Offer> getOffersByCompanyId(int companyId) {
	    List<Offer> offers = new ArrayList<>();
	    String sql = "SELECT * FROM shuttle_offers WHERE company_id = ?";
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, companyId);
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
	                offer.setPrice(rs.getDouble("price"));
	                offers.add(offer);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return offers;
	}
	
	public List<Offer> searchOffers(String departureCity, String arrivalCity, String departureTime, String arrivalTime, Double price) {
	    List<Offer> offers = new ArrayList<>();
	    String query = "SELECT * FROM shuttle_offers WHERE 1=1";

	    if (departureCity != null && !departureCity.isEmpty()) {
	        query += " AND departure_city LIKE ?";
	    }
	    if (arrivalCity != null && !arrivalCity.isEmpty()) {
	        query += " AND arrival_city LIKE ?";
	    }
	    if (departureTime != null && !departureTime.isEmpty()) {
	        query += " AND departure_time >= ?";
	    }
	    if (arrivalTime != null && !arrivalTime.isEmpty()) {
	        query += " AND arrival_time <= ?";
	    }
	    if (price != null) {
	        query += " AND price <= ?";
	    }

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        int index = 1;

	        if (departureCity != null && !departureCity.isEmpty()) {
	            ps.setString(index++, "%" + departureCity + "%");
	        }
	        if (arrivalCity != null && !arrivalCity.isEmpty()) {
	            ps.setString(index++, "%" + arrivalCity + "%");
	        }
	        if (departureTime != null && !departureTime.isEmpty()) {
	            ps.setString(index++, departureTime);
	        }
	        if (arrivalTime != null && !arrivalTime.isEmpty()) {
	            ps.setString(index++, arrivalTime);
	        }
	        if (price != null) {
	            ps.setDouble(index++, price);
	        }

	        ResultSet rs = ps.executeQuery();
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
                offer.setPrice(rs.getDouble("price"));
                offers.add(offer);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return offers;
	}
	
	public boolean createOffer(Offer offer) {
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
	
	public boolean updateOffer(Offer offer) {
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
