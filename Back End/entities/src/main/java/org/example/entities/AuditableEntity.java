package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
    @Column(name = "isActive", nullable = false)
    protected Boolean isActive;
    @CreatedBy
    @Column(name = "createdBy",length = 200,nullable = false)
    protected String createdBy;
    @CreatedDate
    @Column(name = "createdOn",nullable = false)
    protected LocalDateTime createdOn;
    @LastModifiedBy
    @Column(name = "updatedBy",length = 200,nullable = true)
    protected String updatedBy;
    @LastModifiedDate
    @Column(name = "updatedOn",nullable = true)
    protected LocalDateTime updateOn;
}
