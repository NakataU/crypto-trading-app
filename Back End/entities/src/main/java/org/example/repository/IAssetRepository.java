package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.entities.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.beans.Transient;
import java.util.Optional;

@Repository
public interface IAssetRepository extends JpaRepository<Asset, Long> {

    @Query("SELECT a FROM Asset a WHERE a.isActive = true")
    Page<Asset> findAllActive(Pageable pageable);

    @Query("SELECT a FROM Asset a WHERE a.isActive = true AND a.id = :id")
    Optional<Asset> findByIdIsActive(@Param("id") Long id);

    @Query("SELECT a FROM Asset a WHERE a.isActive = true AND a.userId = :id")
    Page<Asset> findByUserIdIsActive(@Param("id") Long id, Pageable pageable);

    @Query("SELECT a FROM Asset a WHERE a.isActive = true AND a.name = :name AND a.userId = :userId")
    Optional<Asset> findByName(@Param("name") String name, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Asset a SET a.isActive = false WHERE a.userId = :userId")
    void deactivateAssetsByUserId(@Param("userId") Long userId);
}
