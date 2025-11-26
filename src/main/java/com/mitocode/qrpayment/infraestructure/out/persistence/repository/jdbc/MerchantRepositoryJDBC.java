package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.infraestructure.out.persistence.config.DbConfig;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.MerchantEntity;

public class MerchantRepositoryJDBC {
	private static final Logger logger = Logger.getLogger(MerchantRepositoryJDBC.class.getName());
	public MerchantEntity save(MerchantEntity merchantEntity) {
        String sql = "INSERT INTO merchants (merchantId, email, name, type, callbackUrl, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, merchantEntity.getMerchantId());
            ps.setString(2, merchantEntity.getEmail());
            ps.setString(3, merchantEntity.getName());
            ps.setString(4, merchantEntity.getType().name());
            ps.setString(5, merchantEntity.getCallBackUrl());
            ps.setString(6, merchantEntity.getStatus().name());

            ps.executeUpdate();
            return merchantEntity;

        } catch (Exception ex) {
            throw new RuntimeException("Error saving merchant: " + ex.getMessage(), ex);
        }
    }

    public MerchantEntity update(MerchantEntity merchantEntity) {
        String sql = "UPDATE merchants SET email = ?, name = ?, type = ?, callbackUrl = ?, status = ? WHERE merchantId = ?";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, merchantEntity.getEmail());
            ps.setString(2, merchantEntity.getName());
            ps.setString(3, merchantEntity.getType().name());
            ps.setString(4, merchantEntity.getCallBackUrl());
            ps.setString(5, merchantEntity.getStatus().name());
            ps.setString(6, merchantEntity.getMerchantId());

            ps.executeUpdate();
            return merchantEntity;

        } catch (Exception ex) {
            throw new RuntimeException("Error updating merchant: " + ex.getMessage(), ex);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM merchants WHERE email = ? LIMIT 1";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error checking email existence: " + ex.getMessage(), ex);
        }
    }

    public boolean existsById(String id) {
        String sql = "SELECT 1 FROM merchants WHERE merchantId = ? LIMIT 1";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error checking ID existence: " + ex.getMessage(), ex);
        }
    }

    public Optional<MerchantEntity> findById(String id) {
        String sql = "SELECT * FROM merchants WHERE merchantId = ?";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                } else {
                    return Optional.empty();
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error finding merchant by ID: " + ex.getMessage(), ex);
        }
    }

    public List<MerchantEntity> findAll() {
        String sql = "SELECT * FROM merchants";
        List<MerchantEntity> merchants = new ArrayList<>();

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                merchants.add(mapResultSetToEntity(rs));
            }
            return merchants;

        } catch (Exception ex) {
            throw new RuntimeException("Error listing merchants: " + ex.getMessage(), ex);
        }
    }

    private MerchantEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        MerchantEntity entity = new MerchantEntity();
        entity.setMerchantId(rs.getString("merchantId"));
        entity.setEmail(rs.getString("email"));
        entity.setName(rs.getString("name"));
        entity.setType(MerchantType.valueOf(rs.getString("type")));
        entity.setCallBackUrl(rs.getString("callbackUrl"));
        entity.setStatus(MerchantStatus.valueOf(rs.getString("status")));
        return entity;
    }

}
