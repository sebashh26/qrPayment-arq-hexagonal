package com.mitocode.qrpayment.infraestructure.out.proxy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.qrpayment.domain.model.enums.PaymentStatus;
import com.mitocode.qrpayment.domain.model.vo.PaymentConfirmation;
import com.mitocode.qrpayment.infraestructure.out.proxy.exception.ProxyException;

@ExtendWith(MockitoExtension.class)
class MerchantProxyImplTest {

    @Mock
    HttpClient httpClient;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    MerchantProxyImpl proxy;

    @Test
    @DisplayName("confirmedPayment: éxito no lanza excepción")
    void confirmedPayment_success() throws Exception {
        // preparar response mock
        @SuppressWarnings("unchecked")
        HttpResponse<String> response = (HttpResponse<String>) mock(HttpResponse.class);
        //when(response.body()).thenReturn("ok");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(response);

        // objectMapper devuelve un JSON válido
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"ok\":true}");

        // mock PaymentConfirmation con valores válidos (usar description del enum)
        PaymentConfirmation confirmation = mock(PaymentConfirmation.class);
        when(confirmation.getCurrency()).thenReturn("USD");
        when(confirmation.getPaymentId()).thenReturn("pay-1");
        when(confirmation.getPurchaseOrderId()).thenReturn("po-1");
        when(confirmation.getAmount()).thenReturn(new BigDecimal("10.00"));
        LocalDateTime fixed = LocalDateTime.of(2024, 12, 1, 10, 0);
        when(confirmation.getAuthorizedAt()).thenReturn(fixed);

        when(confirmation.getStatus()).thenReturn(PaymentStatus.AUTHORIZED.getDescription());

        assertDoesNotThrow(() -> proxy.confirmedPayment(confirmation));

        // opcional: verificar que se llamó al httpClient
        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    @DisplayName("confirmedPayment: InterruptedException se envuelve en ProxyException")
    void confirmedPayment_interruptedException_wrapped() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("inter2"));

        when(objectMapper.writeValueAsString(any())).thenReturn("{\"ok\":true}");

        PaymentConfirmation confirmation = mock(PaymentConfirmation.class);
        when(confirmation.getCurrency()).thenReturn("USD");
        when(confirmation.getPaymentId()).thenReturn("pay-1");
        when(confirmation.getPurchaseOrderId()).thenReturn("po-1");
        when(confirmation.getAmount()).thenReturn(new BigDecimal("10.00"));
        LocalDateTime fixed = LocalDateTime.of(2024, 12, 1, 10, 0);
        when(confirmation.getAuthorizedAt()).thenReturn(fixed);

        when(confirmation.getStatus()).thenReturn(PaymentStatus.AUTHORIZED.getDescription());

        ProxyException ex = assertThrows(ProxyException.class, () -> proxy.confirmedPayment(confirmation));
        assertTrue(ex.getMessage().contains("inter2"));
    }

    @Test
    @DisplayName("confirmedPayment: IOException se envuelve en ProxyException")
    void confirmedPayment_ioException_wrapped() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("io-error"));

        when(objectMapper.writeValueAsString(any())).thenReturn("{\"ok\":true}");

        PaymentConfirmation confirmation = mock(PaymentConfirmation.class);
        when(confirmation.getCurrency()).thenReturn("USD");
        when(confirmation.getPaymentId()).thenReturn("pay-1");
        when(confirmation.getPurchaseOrderId()).thenReturn("po-1");
        when(confirmation.getAmount()).thenReturn(new BigDecimal("10.00"));
        LocalDateTime fixed = LocalDateTime.of(2024, 12, 1, 10, 0);
        when(confirmation.getAuthorizedAt()).thenReturn(fixed);

        when(confirmation.getStatus()).thenReturn(PaymentStatus.AUTHORIZED.getDescription());

        ProxyException ex = assertThrows(ProxyException.class, () -> proxy.confirmedPayment(confirmation));
        assertTrue(ex.getMessage().contains("io-error"));
    }
}