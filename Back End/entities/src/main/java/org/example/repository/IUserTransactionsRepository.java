package org.example.repository;

import org.example.compositeKeys.UserTransactionId;
import org.example.entities.UserTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserTransactionsRepository extends JpaRepository<UserTransactions, UserTransactionId> {
}
