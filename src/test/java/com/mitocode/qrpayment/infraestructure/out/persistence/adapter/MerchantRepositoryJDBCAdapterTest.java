package com.mitocode.qrpayment.infraestructure.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc.MerchantRepositoryJDBCAdapter;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.MerchantEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.MerchantEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.MerchantRepositoryJDBC;

class MerchantRepositoryJDBCAdapterTest {
	
	@Test
    @DisplayName("save: mapea Domain -> Entity, delega en JDBC y mapea Entity -> Domain")
    void save_mapsAndDelegates() {
        MerchantRepositoryJDBC jdbc = mock(MerchantRepositoryJDBC.class);
        MerchantRepositoryJDBCAdapter adapter = new MerchantRepositoryJDBCAdapter(jdbc);

        Merchant domainIn = mock(Merchant.class);
        MerchantEntity entityIn = mock(MerchantEntity.class);
        MerchantEntity entityOut = mock(MerchantEntity.class);
        Merchant domainOut = mock(Merchant.class);

        try (MockedStatic<MerchantEntityMapper> mapper = mockStatic(MerchantEntityMapper.class)) {
            mapper.when(() -> MerchantEntityMapper.toEntity(domainIn)).thenReturn(entityIn);
            when(jdbc.save(entityIn)).thenReturn(entityOut);
            mapper.when(() -> MerchantEntityMapper.toDomain(entityOut)).thenReturn(domainOut);

            Merchant result = adapter.save(domainIn);
            assertSame(domainOut, result);

            mapper.verify(() -> MerchantEntityMapper.toEntity(domainIn), times(1));
            verify(jdbc).save(entityIn);
            mapper.verify(() -> MerchantEntityMapper.toDomain(entityOut));
        }
    }

    @Test
    @DisplayName("update: mapea Domain -> Entity, delega en JDBC y mapea Entity -> Domain")
    void update_mapsAndDelegates() {
        MerchantRepositoryJDBC jdbc = mock(MerchantRepositoryJDBC.class);
        MerchantRepositoryJDBCAdapter adapter = new MerchantRepositoryJDBCAdapter(jdbc);

        Merchant domainIn = mock(Merchant.class);
        MerchantEntity entityIn = mock(MerchantEntity.class);
        MerchantEntity entityOut = mock(MerchantEntity.class);
        Merchant domainOut = mock(Merchant.class);

        try (MockedStatic<MerchantEntityMapper> mapper = mockStatic(MerchantEntityMapper.class)) {
            mapper.when(() -> MerchantEntityMapper.toEntity(domainIn)).thenReturn(entityIn);
            when(jdbc.update(entityIn)).thenReturn(entityOut);
            mapper.when(() -> MerchantEntityMapper.toDomain(entityOut)).thenReturn(domainOut);

            Merchant result = adapter.update(domainIn);
            assertSame(domainOut, result);
        }
    }

    @Test
    @DisplayName("existsByEmail/existsById: delega en JDBC")
    void exists_delegates() {
        MerchantRepositoryJDBC jdbc = mock(MerchantRepositoryJDBC.class);
        MerchantRepositoryJDBCAdapter adapter = new MerchantRepositoryJDBCAdapter(jdbc);

        when(jdbc.existsByEmail("a@test.com")).thenReturn(true);
        when(jdbc.existsById("m-1")).thenReturn(false);

        assertTrue(adapter.existsByEmail("a@test.com"));
        assertFalse(adapter.existsById("m-1"));

        verify(jdbc).existsByEmail("a@test.com");
        verify(jdbc).existsById("m-1");
    }

    @Test
    @DisplayName("findById: presente se mapea a Domain, vacío retorna Optional.empty")
    void findById_mapsOptional() {
        MerchantRepositoryJDBC jdbc = mock(MerchantRepositoryJDBC.class);
        MerchantRepositoryJDBCAdapter adapter = new MerchantRepositoryJDBCAdapter(jdbc);

        MerchantEntity entity = mock(MerchantEntity.class);
        Merchant domain = mock(Merchant.class);

        try (MockedStatic<MerchantEntityMapper> mapper = mockStatic(MerchantEntityMapper.class)) {
            when(jdbc.findById("m-1")).thenReturn(Optional.of(entity));
            mapper.when(() -> MerchantEntityMapper.toDomain(entity)).thenReturn(domain);

            Optional<Merchant> found = adapter.findById("m-1");
            assertTrue(found.isPresent());
            assertSame(domain, found.get());

            when(jdbc.findById("m-x")).thenReturn(Optional.empty());
            Optional<Merchant> missing = adapter.findById("m-x");
            assertTrue(missing.isEmpty());
        }
    }

    @Test
    @DisplayName("findAll: mapea cada Entity a Domain en orden")
    void findAll_mapsList() {
        MerchantRepositoryJDBC jdbc = mock(MerchantRepositoryJDBC.class);
        MerchantRepositoryJDBCAdapter adapter = new MerchantRepositoryJDBCAdapter(jdbc);

        MerchantEntity e1 = mock(MerchantEntity.class);
        MerchantEntity e2 = mock(MerchantEntity.class);
        Merchant d1 = mock(Merchant.class);
        Merchant d2 = mock(Merchant.class);

        try (MockedStatic<MerchantEntityMapper> mapper = mockStatic(MerchantEntityMapper.class)) {
            when(jdbc.findAll()).thenReturn(Arrays.asList(e1, e2));
            mapper.when(() -> MerchantEntityMapper.toDomain(e1)).thenReturn(d1);
            mapper.when(() -> MerchantEntityMapper.toDomain(e2)).thenReturn(d2);

            List<Merchant> result = adapter.findAll();
            assertEquals(2, result.size());
            assertSame(d1, result.get(0));
            assertSame(d2, result.get(1));
        }
    }

}
