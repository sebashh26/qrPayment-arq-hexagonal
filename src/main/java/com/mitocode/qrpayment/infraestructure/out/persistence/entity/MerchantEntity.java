package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mitocode.qrpayment.domain.model.enums.MerchantStatus;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "merchants")
public class MerchantEntity extends BaseAuditingEntity {
	
	@Id
	@Column(name = "merchantid", length = 36)
	private String merchantId;
	@Column(name = "email", nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 100)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MerchantType type;
    @Column(name = "callback_url", length = 500)
    private String callBackUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MerchantStatus status;
  
	public MerchantEntity(String merchantId, String email, String name, MerchantType type, String callBackUrl,
			MerchantStatus status) {
		super();
		this.merchantId = merchantId;
		this.email = email;
		this.name = name;
		this.type = type;
		this.callBackUrl = callBackUrl;
		this.status = status;
	}

}
