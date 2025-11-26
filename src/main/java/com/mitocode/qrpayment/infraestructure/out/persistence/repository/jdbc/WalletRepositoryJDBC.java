package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import com.mitocode.qrpayment.domain.model.enums.WalletStatus;
import com.mitocode.qrpayment.infraestructure.out.persistence.config.DbConfig;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.WalletEntity;

public class WalletRepositoryJDBC {
	
	public WalletEntity save(WalletEntity entity) {
        String sql = "INSERT INTO wallets (walletId, name, description, status) VALUES (?, ?, ?, ?)";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entity.getWalletId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
            ps.setString(4, entity.getStatus().name());

            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error saving walelt: " + e.getMessage(), e);
        }
    }


    public Optional<WalletEntity> findById(String walletId) {
        String sql = "SELECT * FROM wallets WHERE walletId = ?";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, walletId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    WalletEntity qr = new WalletEntity();
                    qr.setWalletId(rs.getString("walletId"));
                    qr.setName(rs.getString("name"));
                    qr.setDescription(rs.getString("description"));
                    qr.setStatus(WalletStatus.valueOf(rs.getString("status")));
                    return Optional.of(qr);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            System.out.println("ERror de wallet " + e.getLocalizedMessage());
            throw new RuntimeException("Error finding Wallet by ID: " + e.getMessage(), e);
        }
    }

}
