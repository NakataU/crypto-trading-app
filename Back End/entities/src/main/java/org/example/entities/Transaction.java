package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.constants.TransactionType;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="transactions")
public class Transaction extends BaseEntity{
    @Column(name = "transaction_type", nullable = false)
    private TransactionType type;
    @Column(name = "asset_name", nullable = false)
    private String assetName;
    @Column(name = "asset_symbol", nullable = false)
    private String assetSymbol;
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
