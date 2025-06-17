package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.compositeKeys.UserTransactionId;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user_transactions")
@IdClass(UserTransactionId.class)
public class UserTransactions extends AuditableEntity{
    @Id
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Id
    @Column(name = "transaction_id",nullable = false)
    private Long transactionId;
}
