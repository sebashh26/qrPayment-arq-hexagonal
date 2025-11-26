package com.mitocode.qrpayment.infraestructure.out.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbConfig {

	private static final String URL = "jdbc:h2:~/paymentdb;DB_CLOSE_DELAY=-1";

	static {
		try (Connection connection = getConnection()) {
			Statement stmt = connection.createStatement();

			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS merchants (
					        merchantId VARCHAR(50) PRIMARY KEY,
					        email VARCHAR(100) NOT NULL UNIQUE,
					        name VARCHAR(100) NOT NULL,
					        type VARCHAR(50) NOT NULL,
					        callbackUrl VARCHAR(200),
					        status VARCHAR(50) NOT NULL
					    )
					""");
			
			stmt.execute("""
	                CREATE TABLE IF NOT EXISTS wallets (
	                    walletId VARCHAR(50) PRIMARY KEY,
	                    name VARCHAR(100) NOT NULL UNIQUE,
	                    description VARCHAR(150) NOT NULL,
	                    status VARCHAR(50) NOT NULL
	                )
	            """);
			//con comillas simples no dobles porque es SQL y genera error
			stmt.execute("""
				    MERGE INTO wallets (walletId, name, description, status)
				    KEY(walletId)
				    VALUES ('2', 'YAPE', 'YAPE PERU', 'ACTIVE')
				""");

			
			stmt.execute("""
	                CREATE TABLE IF NOT EXISTS payments (
	                    paymentId VARCHAR(50) PRIMARY KEY,
	                    merchantId VARCHAR(50) NOT NULL,
	                    qrId VARCHAR(50),
	                    amount DECIMAL(19,2) NOT NULL,
	                    currency VARCHAR(10) NOT NULL,
	                    purchaseOrderId VARCHAR(100),
	                    status VARCHAR(50) NOT NULL,
	                    brandType VARCHAR(50),
	                    walletId VARCHAR(50),
	                    failureReason VARCHAR(500),
	                    authorizedAt TIMESTAMP,
	                    refundedAt TIMESTAMP,
	                    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	                    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
	                )
	            """);

			stmt.execute("""
					    CREATE TABLE IF NOT EXISTS qrcodes (
					        id VARCHAR(50) PRIMARY KEY,
					        merchantId VARCHAR(50) NOT NULL,
					        purchaseOrder VARCHAR(100) NOT NULL,
					        type VARCHAR(20) NOT NULL,
					        currencyCode VARCHAR(10) NOT NULL,
					        amount DECIMAL(10,2) NOT NULL,
					        expirateDate TIMESTAMP NOT NULL,
					        status VARCHAR(20) NOT NULL,
					        qrImage BLOB,
					        qrData VARCHAR(255),
					        FOREIGN KEY (merchantId) REFERENCES merchants(merchantId)
					    )
					""");
		} catch (Exception e) {
			System.out.println("Error " + e.getLocalizedMessage());
            throw new RuntimeException("Error initializing database schema: " + e.getMessage(), e);
		}
	}

	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(URL, "sa", "sa");
	}
}
