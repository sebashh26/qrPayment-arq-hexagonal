package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mitocode.qrpayment.domain.model.enums.WalletStatus;

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
@Table(name = "wallets")
public class WalletEntity extends BaseAuditingEntity{
	@Id
	@Column(name = "walletid")
	private String walletId;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private WalletStatus status;

	public WalletEntity(String walletId, String name, String description, WalletStatus status) {
		this.walletId = walletId;
		this.name = name;
		this.description = description;
		this.status = status;
	}

}
