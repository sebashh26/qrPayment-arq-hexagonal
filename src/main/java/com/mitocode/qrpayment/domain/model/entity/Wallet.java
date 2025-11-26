package com.mitocode.qrpayment.domain.model.entity;

import com.mitocode.qrpayment.domain.model.enums.WalletStatus;
import com.mitocode.qrpayment.domain.model.exception.WalletException;

public class Wallet {
	
	private String walletId;
	private String name;
	private String description;
	private WalletStatus status;
	
	public Wallet(String wallerId, String name, String description, WalletStatus status) {
		this.walletId = wallerId;
		this.name = name;
		this.description = description;
		this.status = status;
	}

	public boolean isActive() {
		return this.status == WalletStatus.ACTIVE;
	}
	
	public void validate() {
        if (!this.isActive()) {
            throw new WalletException("Wallet is not active");
        }
    }

	public String getWallerId() {
		return walletId;
	}

	public void setWallerId(String wallerId) {
		this.walletId = wallerId;
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
