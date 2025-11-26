package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.mitocode.qrpayment.infraestructure.out.persistence.config.DbConfig;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.PaymentEntity;

public class PaymentRepositoryJDBC {
	
	public PaymentEntity save(PaymentEntity entity) {
        String sql = "INSERT INTO payments (paymentId, merchantId, qrId, amount, currency, purchaseOrderId, "
        		+ "status, brandType, walletId, failureReason, authorizedAt, refundedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DbConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, entity.getPaymentId());
            ps.setString(2, entity.getMerchantId());
            ps.setString(3, entity.getQrId());
            ps.setBigDecimal(4, entity.getAmount());
            ps.setString(5, entity.getCurrency().name());
            ps.setString(6, entity.getPurchaseOrderid());
            ps.setString(7, entity.getStatus().name());
            ps.setString(8, entity.getBrandType() != null ? entity.getBrandType().name() : null);
            ps.setString(9, entity.getWalletId());
            ps.setString(10, entity.getFailureReason());
            ps.setTimestamp(11, entity.getAuthorizedAt() != null ? Timestamp.valueOf(entity.getAuthorizedAt()) : null);
            ps.setTimestamp(12, entity.getRefundedAt() != null ? Timestamp.valueOf(entity.getRefundedAt()) : null);

            ps.executeUpdate();
            return entity;

        } catch (Exception e) {
            throw new RuntimeException("Error saving Payment: " + e.getMessage(), e);
        }
    }

}
