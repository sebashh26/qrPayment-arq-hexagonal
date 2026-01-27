package com.mitocode.qrpayment.infraestructure.in.web.context;

import java.util.Collection;

public class AuthorizeContext {

	private AuthorizeContext() {
	}

	private static final ThreadLocal<String> currentClientId = new ThreadLocal<>();
	private static final ThreadLocal<Collection<String>> currentRoles = new ThreadLocal<>();

	public static void setClientId(String clientId) {
		currentClientId.set(clientId);
	}

	public static String getClientId() {
		return currentClientId.get();
	}

	public static void clear() {
		currentClientId.remove();
	}

	public static void setRoles(Collection<String> roles) {
		currentRoles.set(roles);
	}

	public static Collection<String> getRoles() {
		return currentRoles.get();
	}

	public static void clearRoles() {
		currentRoles.remove();
	}


}

