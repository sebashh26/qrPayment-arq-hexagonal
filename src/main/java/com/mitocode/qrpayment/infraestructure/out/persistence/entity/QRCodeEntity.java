package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.mitocode.qrpayment.domain.model.enums.CurrencyCode;
import com.mitocode.qrpayment.domain.model.enums.QRStatus;
import com.mitocode.qrpayment.domain.model.enums.QRType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "qrcodes")
public class QRCodeEntity extends BaseAuditingEntity{

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "merchantid", length = 36)
	private String merchantId;
	@Column(name = "purchaseorder", nullable = false, length = 50)
	private String purchaseOrder;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 20)
	private QRType type;
	@Enumerated(EnumType.STRING)
	@Column(name = "currencycode", nullable = false, length = 20)
	private CurrencyCode currencyCode;
	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;
	@Column(name = "expiratedate")
	private LocalDateTime expirateDate;
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private QRStatus status;
	@Lob
	@Column(name = "qrimage")
	private byte[] qrImage;
	@Column(name = "qrdata")
	private String qrData;

	
	@Override
	public String toString() {
		return "QRCodeEntity{" + "id='" + id + '\'' + ", merchantId='" + merchantId + '\'' + ", purchaseOrder='"
				+ purchaseOrder + '\'' + ", type=" + type + ", currencyCode=" + currencyCode + ", amount=" + amount
				+ ", expirateDate=" + expirateDate + ", status=" + status + ", qrImage=" + Arrays.toString(qrImage)
				+ ", qrData='" + qrData + '\'' + '}';
	}

}
