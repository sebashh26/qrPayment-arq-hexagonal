package com.mitocode.qrpayment.application.usecase.qr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mitocode.qrpayment.application.command.CreateQRCommand;
import com.mitocode.qrpayment.application.dto.QRDto;
import com.mitocode.qrpayment.application.exception.BusinessException;
import com.mitocode.qrpayment.application.mapper.QRCodeToQRDto;
import com.mitocode.qrpayment.domain.model.entity.Merchant;
import com.mitocode.qrpayment.domain.model.entity.QRCode;
import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.MerchantType;
import com.mitocode.qrpayment.domain.model.enums.QRType;
import com.mitocode.qrpayment.domain.port.out.persistence.MerchantRepository;
import com.mitocode.qrpayment.domain.port.out.persistence.QRRepository;
import com.mitocode.qrpayment.domain.port.out.qr.QRGenerator;

@ExtendWith(MockitoExtension.class)
class CreateQRUseCaseTest {

    @Mock
    private QRRepository qrRepository;
    @Mock
    private MerchantRepository merchantRepository;
    @Mock
    private QRGenerator qrGenerator;

    @Mock
    private QRCodeToQRDto mapper;

    @InjectMocks
    private CreateQRUseCase useCase;

    @Test
    void execute_whenMerchantNotFound_throwsBusinessException() {
        when(merchantRepository.findById("missing")).thenReturn(Optional.empty());
        CreateQRCommand cmd = new CreateQRCommand("missing", "PO123", QRType.STATIC, CurrencyCode.PEN, BigDecimal.ONE, LocalDateTime.now().plusMinutes(5));
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(cmd));
        assertEquals("Merchant does not exist", ex.getMessage());
    }

    @Test
    void execute_whenMerchantNotActive_throwsBusinessException() {
        Merchant merchant = new Merchant("Test Merchant", "test@example.com", MerchantType.PHYSICAL, "https://callback.example.com");
        merchant.desactive();
        when(merchantRepository.findById("merchant123")).thenReturn(Optional.of(merchant));
        CreateQRCommand cmd = new CreateQRCommand("merchant123", "PO123", QRType.STATIC, CurrencyCode.PEN, BigDecimal.ONE, LocalDateTime.now().plusMinutes(5));
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(cmd));
        assertEquals("Merchant is not active", ex.getMessage());
    }

    @Test
    void execute_whenDynamicWithoutAmount_throwsInvalidArgumentException() {
        Merchant merchant = new Merchant("Test Merchant", "test@example.com", MerchantType.PHYSICAL, "https://callback.example.com");
        when(merchantRepository.findById("merchant123")).thenReturn(Optional.of(merchant));
        CreateQRCommand cmd = new CreateQRCommand("merchant123", "PO123", QRType.DYNAMIC, CurrencyCode.PEN, null, LocalDateTime.now().plusMinutes(5));
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(cmd));
        assertEquals("Amount must be greater than zero for DYNAMIC QR", ex.getMessage());
    }

    @Test
    void execute_whenDynamicWithoutCurrency_throwsInvalidArgumentException() {
        Merchant merchant = new Merchant("Test Merchant", "test@example.com", MerchantType.PHYSICAL, "https://callback.example.com");
        when(merchantRepository.findById("merchant123")).thenReturn(Optional.of(merchant));
        CreateQRCommand cmd = new CreateQRCommand("merchant123", "PO123", QRType.DYNAMIC, null, BigDecimal.ONE, LocalDateTime.now().plusMinutes(5));
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.execute(cmd));
        assertEquals("CurrencyOrder is required for DYNAMIC QR", ex.getMessage());
    }

    @Test
    void execute_withValidDynamicData_generatesAndSavesQR() {
        Merchant merchant = new Merchant("Test Merchant", "test@example.com", MerchantType.PHYSICAL, "https://callback.example.com");
        when(merchantRepository.findById("merchant123")).thenReturn(Optional.of(merchant));
        byte[] dummyImage = new byte[]{1, 2, 3};
        when(qrGenerator.generateImage(any(String.class))).thenReturn(dummyImage);
        when(qrRepository.save(any(QRCode.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        QRDto dto = new QRDto();
        when(mapper.buildQRDto(any(QRCode.class))).thenReturn(dto);
        CreateQRCommand cmd = new CreateQRCommand("merchant123", "PO123", QRType.DYNAMIC, CurrencyCode.PEN, BigDecimal.TEN, LocalDateTime.now().plusMinutes(5));
        QRDto result = useCase.execute(cmd);
        assertSame(dto, result);
        verify(qrGenerator).generateImage(any(String.class));
        verify(qrRepository).save(any(QRCode.class));
        verify(mapper).buildQRDto(any(QRCode.class));
    }

    @Test
    void execute_withValidStaticData_allowsNullAmountAndCurrency() {
        Merchant merchant = new Merchant("Test Merchant", "test@example.com", MerchantType.PHYSICAL, "https://callback.example.com");
        when(merchantRepository.findById("merchant123")).thenReturn(Optional.of(merchant));
        when(qrGenerator.generateImage(any(String.class))).thenReturn(new byte[]{4, 5, 6});
        when(qrRepository.save(any(QRCode.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        QRDto dto = new QRDto();
        when(mapper.buildQRDto(any(QRCode.class))).thenReturn(dto);
        CreateQRCommand cmd = new CreateQRCommand("merchant123", "PO123", QRType.STATIC, null, null, LocalDateTime.now().plusMinutes(5));
        QRDto result = useCase.execute(cmd);
        assertSame(dto, result);
        verify(qrGenerator).generateImage(any(String.class));
        verify(qrRepository).save(any(QRCode.class));
        verify(mapper).buildQRDto(any(QRCode.class));
    }
}