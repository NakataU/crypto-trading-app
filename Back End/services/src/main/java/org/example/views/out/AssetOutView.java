package org.example.views.out;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetOutView {
    private Long id;
    private String name;
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
}
