package org.example.services;

import org.example.exceptions.EntityNotFound;
import org.example.views.in.TransactionInView;
import org.example.views.out.TransactionOutView;

public interface UserTransactionsService {

    public TransactionOutView buyAsset(TransactionInView transaction, Long userId) throws EntityNotFound;

    public TransactionOutView sellAsset(TransactionInView transaction, Long userId) throws EntityNotFound;
}
