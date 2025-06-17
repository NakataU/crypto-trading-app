package org.example.services;

import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.views.in.AssetInView;
import org.example.views.out.AssetOutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssetService {
    public Page<AssetOutView> getAll(Pageable pageable);

    public Optional<AssetOutView> getById(Long id);

    public AssetOutView addOne(AssetInView asset);

    public AssetOutView updateOne(Long id, AssetInView asset) throws EntityNotFound;

    public void deleteOne(Long id) throws EntityNotActive, EntityNotFound;

    public Page<AssetOutView> getAllByUser(Long userId, Pageable pageable);

    public Optional<AssetOutView> getByName(Long id, String name);

}
