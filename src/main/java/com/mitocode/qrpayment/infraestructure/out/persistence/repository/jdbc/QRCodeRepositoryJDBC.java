package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRStatus;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.infraestructure.out.persistence.config.DbConfig;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.QRCodeEntity;

@Repository
public class QRCodeRepositoryJDBC {
	
	public QRCodeEntity save(QRCodeEntity entity) {
        String sql = "INSERT INTO qrcodes (id, merchantId, purchaseOrder, type, currencyCode, amount, expirateDate, status, qrImage, qrData) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println("entity qr " + entity.toString());

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entity.getId());
            ps.setString(2, entity.getMerchantId());
            ps.setString(3, entity.getPurchaseOrder());
            ps.setString(4, entity.getType().name());
            ps.setString(5, entity.getCurrencyCode().name());
            ps.setBigDecimal(6, entity.getAmount());
            ps.setTimestamp(7, Timestamp.valueOf(entity.getExpirateDate()));
            ps.setString(8, entity.getStatus().name());
            ps.setBytes(9, entity.getQrImage());
            ps.setString(10, entity.getQrData());

            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error saving QR: " + e.getMessage(), e);
        }
    }

    public boolean existsQRCode(String qrId) {
        String sql = "SELECT 1 FROM qrcodes WHERE id = ?";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, qrId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error checking QR existence: " + e.getMessage(), e);
        }
    }

    public Optional<QRCodeEntity> findById(String qrId) {
        System.out.println("String Find QRID "+ qrId);
        String sql = "SELECT * FROM qrcodes WHERE id = ?";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, qrId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    QRCodeEntity qr = new QRCodeEntity();
                    qr.setId(rs.getString("id"));
                    qr.setMerchantId(rs.getString("merchantId"));
                    qr.setPurchaseOrder(rs.getString("purchaseOrder"));
                    qr.setType(QRType.valueOf(rs.getString("type")));
                    qr.setCurrencyCode(CurrencyCode.valueOf(rs.getString("currencyCode")));
                    qr.setAmount(rs.getBigDecimal("amount"));
                    qr.setExpirateDate(rs.getTimestamp("expirateDate").toLocalDateTime());
                    qr.setStatus(QRStatus.valueOf(rs.getString("status")));
                    qr.setQrImage(rs.getBytes("qrImage"));
                    qr.setQrData(rs.getString("qrData"));
                    return Optional.of(qr);
                } else {
                    return Optional.empty();
                }
            }
        } catch (Exception e) {
            System.out.println("Error Find QRID "+ e.getLocalizedMessage());
            throw new RuntimeException("Error finding QR by ID: " + e.getMessage(), e);
        }
    }

    public QRCodeEntity update(QRCodeEntity entity) {
        String sql = "UPDATE qrcodes SET status = ?, qrImage = ? WHERE id = ?";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entity.getStatus().name());
            ps.setBytes(2, entity.getQrImage());
            ps.setString(3, entity.getId());

            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error updating QR: " + e.getMessage(), e);
        }
    }
}
