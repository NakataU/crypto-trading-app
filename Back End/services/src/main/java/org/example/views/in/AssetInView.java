package org.example.views.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.entities.User;

import java.math.BigDecimal;

@Data
public class AssetInView {
    @NotNull
    private String name;
    @NotNull
    private String symbol;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private Long userId;
}
