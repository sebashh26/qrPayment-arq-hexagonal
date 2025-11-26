package com.mitocode.qrpayment.infraestructure.out.persistence.mapper;

import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.PaymentEntity;

public class PaymentEntityMapper {
	
	public static PaymentEntity toEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setPaymentId(payment.getPaymentId());
        entity.setQrId(payment.getQrId());
        entity.setWalletId(payment.getWalletId());
        entity.setMerchantId(payment.getMerchantId());
        entity.setPurchaseOrderid(payment.getPurchaseOrderId());
        entity.setAmount(payment.getAmount());
        entity.setCurrency(payment.getCurrency());
        entity.setBrandType(payment.getBrandType());
        entity.setStatus(payment.getStatus());
        entity.setAuthorizedAt(payment.getAuthorizedAt());
        entity.setRefundedAt(payment.getRefundedAt());
        return entity;
    }

    public static Payment toDomain(PaymentEntity entity) {
        Payment payment = new Payment(
                entity.getMerchantId(),
                entity.getQrId(),
                entity.getAmount() ,
                entity.getCurrency(),
                entity.getPurchaseOrderid(),
                entity.getStatus(),
                entity.getBrandType(),
                entity.getWalletId(),
                entity.getFailureReason(),
                entity.getAuthorizedAt(),
                entity.getRefundedAt()
        );
        payment.setPaymentId(entity.getPaymentId());
        return payment;
    }

}
