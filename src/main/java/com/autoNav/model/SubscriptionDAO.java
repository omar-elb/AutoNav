package com.autoNav.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.autoNav.util.DBConnection;

public class SubscriptionDAO {
	public boolean addSubscription(int userId, int offerId) {
        String sql = "INSERT INTO subscriptions (user_id, offer_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setInt(1, userId);
            ps.setInt(2, offerId);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
