package com.mitocode.qrpayment.infraestructure.out.persistence.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditingEntity {
	
	@CreatedBy
    @Column(name = "created_by", length = 100, updatable = false)
    private String createdBy;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = true)
    private LocalDateTime createdAt;
    
    @LastModifiedBy
    @Column(name = "updated_by", length = 100)
    private String updatedBy;
    
    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

}
