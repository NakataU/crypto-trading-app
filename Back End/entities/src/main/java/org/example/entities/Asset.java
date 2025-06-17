package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="assets")
public class Asset extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "symbol", nullable = false)
    private String symbol;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;
    @Column(name = "user_id", nullable = false)
    private Long userId;
}
