package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class RequestReverseEntity {

    private String merchantId;
    private int fechaTransaction;
    private String qrId;
    private int ttl;

    @DynamoDbPartitionKey
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    @DynamoDbSortKey
    public int getFechaTransaction() {
    	// Solo calcula si el valor es 0 (primera vez)
        if (this.fechaTransaction == 0) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            this.fechaTransaction = Integer.parseInt(now.format(formatter));
        }
        return fechaTransaction;
    }

    public void setFechaTransaction(int fechaTransaction) {
		this.fechaTransaction = fechaTransaction;
    }

    @DynamoDbAttribute("qrId")
    public String getQrId() {
        return qrId;
    }

    public void setQrId(String qrId) {
        this.qrId = qrId;
    }

    @DynamoDbAttribute("ttl")
    public int getTtl() {
    	 // Solo calcula si no ha sido seteado
        if (this.ttl == 0) {
            this.ttl = (int) (Instant.now().getEpochSecond() + 1800);
        }
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
