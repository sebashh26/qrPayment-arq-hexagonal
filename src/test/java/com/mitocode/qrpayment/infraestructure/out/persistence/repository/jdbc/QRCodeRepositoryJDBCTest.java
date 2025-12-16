package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRStatus;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.infraestructure.out.persistence.config.DbConfig;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.QRCodeEntity;

public class QRCodeRepositoryJDBCTest {
	@Test
	@DisplayName("save: setea parámetros y ejecuta INSERT")
	void save_setsParams_and_executesInsert() throws Exception {
		QRCodeRepositoryJDBC repo = new QRCodeRepositoryJDBC();

		QRCodeEntity e = new QRCodeEntity();
		e.setId("qr-1");
		e.setMerchantId("m-1");
		e.setPurchaseOrder("PO-1");
		e.setType(QRType.values()[0]);
		e.setCurrencyCode(CurrencyCode.values()[0]);
		e.setAmount(new BigDecimal("12.34"));
		LocalDateTime ts = LocalDateTime.now().withNano(0);
		e.setExpirateDate(ts);
		e.setStatus(QRStatus.values()[0]);
		e.setQrImage(new byte[] { 1, 2 });
		e.setQrData("QRDATA-1");

		Connection con = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);

		try (MockedStatic<DbConfig> db = mockStatic(DbConfig.class)) {
			db.when(DbConfig::getConnection).thenReturn(con);
			when(con.prepareStatement(anyString())).thenReturn(ps);
			when(ps.executeUpdate()).thenReturn(1);

			QRCodeEntity saved = repo.save(e);
			assertSame(e, saved);

			verify(ps).setString(1, "qr-1");
			verify(ps).setString(2, "m-1");
			verify(ps).setString(3, "PO-1");
			verify(ps).setString(4, e.getType().name());
			verify(ps).setString(5, e.getCurrencyCode().name());
			verify(ps).setBigDecimal(6, new BigDecimal("12.34"));
			verify(ps).setTimestamp(7, Timestamp.valueOf(ts));
			verify(ps).setString(8, e.getStatus().name());
			verify(ps).setBytes(9, new byte[] { 1, 2 });
			verify(ps).setString(10, "QRDATA-1");
			verify(ps).executeUpdate();
		}
	}

	@Test
	@DisplayName("existsQRCode: true/false según ResultSet.next()")
	void existsQRCode_variants() throws Exception {
		QRCodeRepositoryJDBC repo = new QRCodeRepositoryJDBC();

		Connection con = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);

		// true
		try (MockedStatic<DbConfig> db = mockStatic(DbConfig.class)) {
			db.when(DbConfig::getConnection).thenReturn(con);
			when(con.prepareStatement(anyString())).thenReturn(ps);
			when(ps.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true);

			assertTrue(repo.existsQRCode("qr-1"));
			verify(ps).setString(1, "qr-1");
		}

		// false
		Connection con2 = mock(Connection.class);
		PreparedStatement ps2 = mock(PreparedStatement.class);
		ResultSet rs2 = mock(ResultSet.class);
		try (MockedStatic<DbConfig> db2 = mockStatic(DbConfig.class)) {
			db2.when(DbConfig::getConnection).thenReturn(con2);
			when(con2.prepareStatement(anyString())).thenReturn(ps2);
			when(ps2.executeQuery()).thenReturn(rs2);
			when(rs2.next()).thenReturn(false);

			assertFalse(repo.existsQRCode("qr-x"));
			verify(ps2).setString(1, "qr-x");
		}
	}

	@Test
	@DisplayName("findById: presente mapea columnas correctamente, vacío Optional.empty")
	void findById_present_and_empty() throws Exception {
		QRCodeRepositoryJDBC repo = new QRCodeRepositoryJDBC();

		// Present
		Connection con = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);
		ResultSet rs = mock(ResultSet.class);
		LocalDateTime ts = LocalDateTime.now().withNano(0);

		try (MockedStatic<DbConfig> db = mockStatic(DbConfig.class)) {
			db.when(DbConfig::getConnection).thenReturn(con);
			when(con.prepareStatement(startsWith("SELECT * FROM qrcodes"))).thenReturn(ps);
			when(ps.executeQuery()).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("id")).thenReturn("qr-1");
			when(rs.getString("merchantId")).thenReturn("m-1");
			when(rs.getString("purchaseOrder")).thenReturn("PO-1");
			when(rs.getString("type")).thenReturn(QRType.DYNAMIC.name());
			when(rs.getString("currencyCode")).thenReturn(CurrencyCode.USD.name());
			when(rs.getBigDecimal("amount")).thenReturn(new BigDecimal("10.00"));
			when(rs.getTimestamp("expirateDate")).thenReturn(Timestamp.valueOf(ts));
			when(rs.getString("status")).thenReturn(QRStatus.ACTIVE.name());
			when(rs.getBytes("qrImage")).thenReturn(new byte[] { 5 });
			when(rs.getString("qrData")).thenReturn("QRDATA-1");

			Optional<QRCodeEntity> found = repo.findById("qr-1");
			assertTrue(found.isPresent());
			QRCodeEntity qr = found.get();
			assertEquals("qr-1", qr.getId());
			assertEquals("m-1", qr.getMerchantId());
			assertEquals("PO-1", qr.getPurchaseOrder());
			assertEquals(QRType.DYNAMIC, qr.getType());
			assertEquals(CurrencyCode.USD, qr.getCurrencyCode());
			assertEquals(0, new BigDecimal("10.00").compareTo(qr.getAmount()));
			assertEquals(ts, qr.getExpirateDate());
			assertEquals(QRStatus.ACTIVE, qr.getStatus());
			assertArrayEquals(new byte[] { 5 }, qr.getQrImage());
			assertEquals("QRDATA-1", qr.getQrData());
		}

		// Empty
		Connection con2 = mock(Connection.class);
		PreparedStatement ps2 = mock(PreparedStatement.class);
		ResultSet rs2 = mock(ResultSet.class);

		try (MockedStatic<DbConfig> db2 = mockStatic(DbConfig.class)) {
			db2.when(DbConfig::getConnection).thenReturn(con2);
			when(con2.prepareStatement(anyString())).thenReturn(ps2);
			when(ps2.executeQuery()).thenReturn(rs2);
			when(rs2.next()).thenReturn(false);

			Optional<QRCodeEntity> missing = repo.findById("qr-x");
			assertTrue(missing.isEmpty());
		}
	}

	@Test
	@DisplayName("update: setea status, qrImage e id")
	void update_setsParams() throws Exception {
		QRCodeRepositoryJDBC repo = new QRCodeRepositoryJDBC();

		QRCodeEntity e = new QRCodeEntity();
		e.setId("qr-u");
		e.setStatus(QRStatus.INACTIVE);
		e.setQrImage(new byte[] { 9 });

		Connection con = mock(Connection.class);
		PreparedStatement ps = mock(PreparedStatement.class);

		try (MockedStatic<DbConfig> db = mockStatic(DbConfig.class)) {
			db.when(DbConfig::getConnection).thenReturn(con);
			when(con.prepareStatement(anyString())).thenReturn(ps);
			when(ps.executeUpdate()).thenReturn(1);

			QRCodeEntity updated = repo.update(e);
			assertSame(e, updated);

			verify(ps).setString(1, QRStatus.INACTIVE.name());
			verify(ps).setBytes(2, new byte[] { 9 });
			verify(ps).setString(3, "qr-u");
			verify(ps).executeUpdate();
		}
	}
}
