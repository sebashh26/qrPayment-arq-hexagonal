package com.mitocode.qrpayment.infraestructure.out.persistence.mapper;

import com.mitocode.qrpayment.domain.model.entity.AuthorizationInfo;
import com.mitocode.qrpayment.domain.model.entity.Card;
import com.mitocode.qrpayment.domain.model.entity.Order;
import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.PaymentEntity;

public class PaymentEntityMapper {
	
	private PaymentEntityMapper() {}
	
	public static PaymentEntity toEntity(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setPaymentId(payment.getPaymentId());
        entity.setQrId(payment.getQrId());
        entity.setWalletId(payment.getWalletId());
        entity.setMerchantId(payment.getMerchantId());
        entity.setPurchaseOrderid(payment.getOrder().getPurchaseOrderid());
        entity.setAmount(payment.getOrder().getAmount());
        entity.setCurrency(payment.getOrder().getCurrency());
        entity.setBrandType(payment.getCard().getBrand());
        entity.setStatus(payment.getStatus());
        if (payment.getAuthorizationInfo() != null) {
            entity.setAuthorizedAt(payment.getAuthorizationInfo().getAuthorizedAt());
            entity.setFailureReason(payment.getAuthorizationInfo().getFailureReason());
            entity.setRefundedAt(payment.getAuthorizationInfo().getRefundedAt());
        }
        return entity;
    }

    public static Payment toDomain(PaymentEntity entity) {
    	
    	Card card =  Card.builder().brand(entity.getBrandType())
				.build();
        
        Order order =  Order.builder()
        		.orderNumber("ORD-" + entity.getPaymentId().substring(0, 8))
        		.currency(entity.getCurrency())
        		.amount(entity.getAmount())
    			.purchaseOrderid(entity.getPurchaseOrderid())
    			.installments("1").build();
        
        AuthorizationInfo authInfo = AuthorizationInfo.builder()
				.authorizedAt(entity.getAuthorizedAt())
				.failureReason(entity.getFailureReason())
				.refundedAt(entity.getRefundedAt())
				.build();
        
        return Payment.builder()
        		.paymentId(entity.getPaymentId())
        		.merchantId(entity.getMerchantId())
        		.qrId(entity.getQrId())
        		.walletId(entity.getWalletId())
        		.card(card)
        		.order(order)
        		.authorizationInfo(authInfo)
				.status(entity.getStatus())
				.build();
        
         
    }

}
