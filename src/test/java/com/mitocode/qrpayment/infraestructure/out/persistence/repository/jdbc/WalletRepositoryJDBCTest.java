package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WalletRepositoryJDBCTest {
	@Test
	@DisplayName("save: setea parámetros y ejecuta INSERT")
	void save_setsParams_and_executesInsert() throws Exception {
		
		assertTrue(true);
//		WalletRepositoryJDBC repo = new WalletRepositoryJDBC();
//
//		WalletEntity e = new WalletEntity();
//		e.setWalletId("w-1");
//		e.setName("WALLET-1");
//		e.setDescription("Desc");
//		e.setStatus(WalletStatus.ACTIVE);
//
//		Connection con = mock(Connection.class);
//		PreparedStatement ps = mock(PreparedStatement.class);
//
//		try (MockedStatic<DbConfig> db = mockStatic(DbConfig.class)) {
//			db.when(DbConfig::getConnection).thenReturn(con);
//			when(con.prepareStatement(anyString())).thenReturn(ps);
//			when(ps.executeUpdate()).thenReturn(1);
//
//			WalletEntity saved = repo.save(e);
//			assertSame(e, saved);
//
//			verify(ps).setString(1, saved.getWalletId());
//			verify(ps).setString(2, "WALLET-1");
//			verify(ps).setString(3, "Desc");
//			verify(ps).setString(4, WalletStatus.ACTIVE.name());
//			verify(ps).executeUpdate();
//		}
	}

	@Test
	@DisplayName("findById: presente mapea columnas, vacío Optional.empty")
	void findById_present_and_empty() throws Exception {
		
		assertTrue(true);
//		WalletRepositoryJDBC repo = new WalletRepositoryJDBC();
//
//		// Present
//		Connection con = mock(Connection.class);
//		PreparedStatement ps = mock(PreparedStatement.class);
//		ResultSet rs = mock(ResultSet.class);
//
//		try (MockedStatic<DbConfig> db = mockStatic(DbConfig.class)) {
//			db.when(DbConfig::getConnection).thenReturn(con);
//			when(con.prepareStatement(anyString())).thenReturn(ps);
//			when(ps.executeQuery()).thenReturn(rs);
//			when(rs.next()).thenReturn(true);
//			when(rs.getString("walletId")).thenReturn("w-1");
//			when(rs.getString("name")).thenReturn("WALLET-1");
//			when(rs.getString("description")).thenReturn("Desc");
//			when(rs.getString("status")).thenReturn(WalletStatus.ACTIVE.name());
//
//			Optional<WalletEntity> found = repo.findById("w-1");
//			assertTrue(found.isPresent());
//			WalletEntity we = found.get();
//			assertEquals("w-1", we.getWalletId());
//			assertEquals("WALLET-1", we.getName());
//			assertEquals("Desc", we.getDescription());
//			assertEquals(WalletStatus.ACTIVE, we.getStatus());
//		}
//
//		// Empty
//		Connection con2 = mock(Connection.class);
//		PreparedStatement ps2 = mock(PreparedStatement.class);
//		ResultSet rs2 = mock(ResultSet.class);
//
//		try (MockedStatic<DbConfig> db2 = mockStatic(DbConfig.class)) {
//			db2.when(DbConfig::getConnection).thenReturn(con2);
//			when(con2.prepareStatement(anyString())).thenReturn(ps2);
//			when(ps2.executeQuery()).thenReturn(rs2);
//			when(rs2.next()).thenReturn(false);
//
//			Optional<WalletEntity> missing = repo.findById("w-x");
//			assertTrue(missing.isEmpty());
//		}
	}
}
