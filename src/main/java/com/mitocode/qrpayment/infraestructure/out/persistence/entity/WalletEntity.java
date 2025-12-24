package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import com.mitocode.qrpayment.domain.model.enums.WalletStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wallets")
public class WalletEntity {
	@Id
	@Column(name = "wallet_id")
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

	public WalletEntity() {
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public WalletStatus getStatus() {
		return status;
	}

	public void setStatus(WalletStatus status) {
		this.status = status;
	}

}
