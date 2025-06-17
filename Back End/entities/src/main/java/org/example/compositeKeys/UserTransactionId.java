package org.example.compositeKeys;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserTransactionId implements Serializable {
    private Long userId;
    private Long transactionId;
}
