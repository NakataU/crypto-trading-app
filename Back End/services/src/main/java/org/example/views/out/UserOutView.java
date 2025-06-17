package org.example.views.out;

import lombok.Data;
import org.example.entities.Asset;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserOutView {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private BigDecimal balance;
    private BigDecimal freeBalance;
    private List<Asset> assets;
}
