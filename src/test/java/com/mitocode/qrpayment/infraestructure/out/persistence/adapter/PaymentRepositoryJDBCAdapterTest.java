package com.mitocode.qrpayment.infraestructure.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.mitocode.qrpayment.domain.model.entity.Payment;
import com.mitocode.qrpayment.infraestructure.out.persistence.adapter.jdbc.PaymentRepositoryJDBCAdapter;
import com.mitocode.qrpayment.infraestructure.out.persistence.entity.PaymentEntity;
import com.mitocode.qrpayment.infraestructure.out.persistence.mapper.PaymentEntityMapper;
import com.mitocode.qrpayment.infraestructure.out.persistence.repository.jdbc.PaymentRepositoryJDBC;

class PaymentRepositoryJDBCAdapterTest {
	
	@Test
    @DisplayName("save: mapea Domain -> Entity, delega JDBC y mapea Entity -> Domain")
    void save_mapsAndDelegates() {
        PaymentRepositoryJDBC jdbc = mock(PaymentRepositoryJDBC.class);
        PaymentRepositoryJDBCAdapter adapter = new PaymentRepositoryJDBCAdapter(jdbc);

        Payment domainIn = mock(Payment.class);
        PaymentEntity entityIn = mock(PaymentEntity.class);
        PaymentEntity entityOut = mock(PaymentEntity.class);
        Payment domainOut = mock(Payment.class);

        try (MockedStatic<PaymentEntityMapper> mapper = mockStatic(PaymentEntityMapper.class)) {
            mapper.when(() -> PaymentEntityMapper.toEntity(domainIn)).thenReturn(entityIn);
            when(jdbc.save(entityIn)).thenReturn(entityOut);
            mapper.when(() -> PaymentEntityMapper.toDomain(entityOut)).thenReturn(domainOut);

            Payment result = adapter.save(domainIn);
            assertSame(domainOut, result);

            verify(jdbc).save(entityIn);
        }
    }

    @Test
    @DisplayName("findById: aún no implementado retorna null")
    void findById_returnsNull() {
        PaymentRepositoryJDBC jdbc = mock(PaymentRepositoryJDBC.class);
        PaymentRepositoryJDBCAdapter adapter = new PaymentRepositoryJDBCAdapter(jdbc);

        assertNull(adapter.findById(1));
        verifyNoInteractions(jdbc);
    }

    @Test
    @DisplayName("update: aún no implementado retorna null")
    void update_returnsNull() {
        PaymentRepositoryJDBC jdbc = mock(PaymentRepositoryJDBC.class);
        PaymentRepositoryJDBCAdapter adapter = new PaymentRepositoryJDBCAdapter(jdbc);

        Payment p = mock(Payment.class);
        assertNull(adapter.update(p));
        verifyNoInteractions(jdbc);
    }

}
