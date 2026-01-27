package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mitocode.qrpayment.domain.model.enums.BrandType;
import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "payments")
public class PaymentEntity extends BaseAuditingEntity{
	
	@Id
	@Column(name = "paymentid", length = 36)
	private String paymentId;
	@Column(name = "merchantid", length = 36)
    private String merchantId;
	@Column(name = "qrid", length = 36)
    private String qrId;
	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	@Column(name = "currency", nullable = false, length = 20)
    private CurrencyCode currency;
	@Column(name = "purchaseorderid", nullable = false, length = 50)
    private String purchaseOrderid;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;
	@Enumerated(EnumType.STRING)
    @Column(name = "brandtype", nullable = false, length = 20)
    private BrandType brandType;
	@Column(name = "walletid", length = 36)
    private String walletId;
	@Column(name = "failurereason", length = 255)
    private String failureReason;
	@Column(name = "authorizedat")
    private LocalDateTime authorizedAt;
	@Column(name = "refundedat")
    private LocalDateTime refundedAt;
	

    

}
