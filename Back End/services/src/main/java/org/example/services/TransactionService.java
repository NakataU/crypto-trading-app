package org.example.services;

import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.views.in.TransactionInView;
import org.example.views.out.TransactionOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionService {
    public Page<TransactionOutView> getAll(Pageable pageable);

    public Optional<TransactionOutView> getById(Long id);

    public TransactionOutView addOne(TransactionInView transaction, Long userId) throws EntityNotFound;

    public TransactionOutView updateOne(Long id, TransactionInView user) throws EntityNotFound;

    public void deleteOne(Long id) throws EntityNotActive, EntityNotFound;

    public Page<TransactionOutView> getTransactionByUserId(Long userId, Pageable pageable);

}
