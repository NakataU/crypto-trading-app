package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.isActive = true")
    Page<Transaction> findAllActive(Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.isActive = true AND t.id = :id")
    Optional<Transaction> findByIdIsActive(@Param("id") Long id);

    @Query("""
                SELECT t
                FROM UserTransactions ut
                JOIN Transaction t ON ut.transactionId = t.id
                WHERE ut.userId = :id AND t.isActive = true
            """)
    Page<Transaction> findAllByUserId(Pageable pageable, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("""
    UPDATE Transaction t
    SET t.isActive = false
    WHERE t.id IN (
        SELECT ut.transactionId
        FROM UserTransactions ut
        WHERE ut.userId = :userId
    )
    """)
    void deactivateTransactionsByUserId(@Param("userId") Long userId);
}
