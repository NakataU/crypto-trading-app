package org.example.views.out;

import lombok.Data;
import org.example.constants.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionOutView {
    private Long id;
    private TransactionType type;
    private String assetName;
    private String assetSymbol;
    private BigDecimal quantity;
    private BigDecimal price;
    private LocalDateTime createdOn;
}
