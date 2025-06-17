package org.example.views.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.constants.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionInView {
    @NotNull
    private TransactionType type;
    @NotNull
    private String assetName;
    @NotNull
    private String assetSymbol;
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private BigDecimal price;
}
