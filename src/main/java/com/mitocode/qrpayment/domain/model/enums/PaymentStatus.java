package com.mitocode.qrpayment.domain.model.enums;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PaymentStatus {
	
	AUTHORIZED("Authorized"),
	PENDING("Pendiente"),
	DENIED("Denied"),
	REFUNDED("Refunded");
	
	private final String description;
	private static final Map<String, PaymentStatus> BY_DESCRIPTION;

	static {
        BY_DESCRIPTION = Arrays.stream(values())
                .collect(Collectors.toMap(
                        ps -> ps.description.toLowerCase(Locale.ROOT),
                        Function.identity()
                ));
    }

	
	PaymentStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static PaymentStatus fromDescription(String desc) {
        if (desc == null) {
            throw new IllegalArgumentException("PaymentStatus description is null");
        }
        PaymentStatus status = BY_DESCRIPTION.get(desc.toLowerCase(Locale.ROOT));
        if (status == null) {
            throw new IllegalArgumentException("Invalid payment status: " + desc);
        }
        return status;
    }

	
}
