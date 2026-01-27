package com.mitocode.qrpayment.domain.model.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mitocode.qrpayment.domain.model.enums.WalletStatus;
import com.mitocode.qrpayment.domain.model.exception.WalletException;

class WalletTest {
	
	@Test
    void isActive_whenStatusIsActive_returnsTrue() {
        Wallet wallet = new Wallet("wallet1", "Test Wallet", "A test wallet", WalletStatus.ACTIVE);
        assertTrue(wallet.isActive());
    }

    @Test
    void isActive_whenStatusIsInactive_returnsFalse() {
        Wallet wallet = new Wallet("wallet1", "Test Wallet", "A test wallet", WalletStatus.INACTIVE);
        assertFalse(wallet.isActive());
    }

    @Test
    void validate_whenWalletIsInactive_throwsException() {
        Wallet wallet = new Wallet("wallet1", "Test Wallet", "A test wallet", WalletStatus.INACTIVE);
        assertThrows(WalletException.class, wallet::validate);
    }

    @Test
    void validate_whenWalletIsActive_doesNotThrow() {
        Wallet wallet = new Wallet("wallet1", "Test Wallet", "A test wallet", WalletStatus.ACTIVE);
        wallet.validate();
    }

}
