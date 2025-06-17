package org.example.services.impl;

import org.example.entities.Asset;
import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.repository.IAssetRepository;
import org.example.services.AssetService;
import org.example.views.in.AssetInView;
import org.example.views.out.AssetOutView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AssetServiceImpl implements AssetService {
    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private IAssetRepository assetRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<AssetOutView> getAll(Pageable pageable) {
        String logId = UUID.randomUUID().toString();

        log.info("{} : getAll start", logId);
        try {
            Page<Asset> Assets = assetRepository.findAllActive(pageable);
            log.debug("{} : retrieved {} Assets", logId, Assets.getSize());

            List<AssetOutView> list = modelMapper.map(Assets.getContent(),
                    new TypeToken<List<AssetOutView>>() {
                    }.getType());
            Page<AssetOutView> res = new PageImpl<>(list, pageable, Assets.getTotalElements());

            log.debug("{} : mapped to {} AssetOutView", logId, res.getSize());
            return res;
        } catch (Exception e) {
            log.error("{} getAll error", logId, e);
            throw e;
        } finally {
            log.info("{} : getAll finished", logId);
        }
    }

    @Override
    public Optional<AssetOutView> getById(Long id) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Optional<Asset> entity = assetRepository.findByIdIsActive(id);
            Optional<AssetOutView> res = modelMapper.map(entity, new TypeToken<Optional<AssetOutView>>() {
            }.getType());
            return res;

        } catch (Exception e) {
            log.error("{} : getById error", logId, e);
            throw e;
        } finally {
            log.info("{} : getById finished", logId);
        }
    }

    @Override
    public AssetOutView addOne(AssetInView Asset) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: body : {}", logId, Asset);

        try {
            Asset entity = modelMapper.map(Asset, new TypeToken<Asset>() {
            }.getType());
            entity.setIsActive(true);
            entity = assetRepository.save(entity);
            AssetOutView res = modelMapper.map(entity, new TypeToken<AssetOutView>() {
            }.getType());
            return res;

        } catch (Exception e) {
            log.error("{} : addOne error", logId, e);
            throw e;
        } finally {
            log.info("{} : addOne finished", logId);
        }
    }

    @Override
    public AssetOutView updateOne(Long id, AssetInView asset) throws EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : updateOne start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Asset entity = assetRepository.findByIdIsActive(id).orElseThrow(() -> new EntityNotFound());
            Asset toSave = modelMapper.map(asset, new TypeToken<Asset>() {
            }.getType());
            toSave.setId(id);
            toSave.setUserId(asset.getUserId());
            toSave.setIsActive(entity.getIsActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            toSave = assetRepository.save(toSave);

            AssetOutView res = modelMapper.map(toSave, new TypeToken<AssetOutView>() {
            }.getType());

            return res;

        } catch (Exception e) {
            log.error("{} : updateOne error", logId, e);
            throw e;
        } finally {
            log.info("{} : updateOne finished", logId);
        }
    }

    @Override
    public void deleteOne(Long id) throws EntityNotActive, EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : deleteOne start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Asset entity = assetRepository.findById(id).orElseThrow(() -> new EntityNotFound());
            if (!entity.getIsActive()) {
                throw new EntityNotActive();
            }
            entity.setIsActive(false);
            assetRepository.save(entity);

        } catch (Exception e) {
            log.error("{} : deleteOne error", logId, e);
            throw e;
        } finally {
            log.info("{} : deleteOne finished", logId);
        }
    }

    @Override
    public Page<AssetOutView> getAllByUser(Long userId, Pageable pageable) {
        String logId = UUID.randomUUID().toString();

        log.info("{} : getAllByUser start", logId);
        try {
            Page<Asset> Assets = assetRepository.findByUserIdIsActive(userId,pageable);
            log.debug("{} : retrieved {} Assets", logId, Assets.getSize());

            List<AssetOutView> list = modelMapper.map(Assets.getContent(),
                    new TypeToken<List<AssetOutView>>() {
                    }.getType());
            Page<AssetOutView> res = new PageImpl<>(list, pageable, Assets.getTotalElements());

            log.debug("{} : mapped to {} AssetOutView", logId, res.getSize());
            return res;
        } catch (Exception e) {
            log.error("{} getAllByUser error", logId, e);
            throw e;
        } finally {
            log.info("{} : getAllByUser finished", logId);
        }
    }

    @Override
    public Optional<AssetOutView> getByName(Long id, String name) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getByName start", logId);
        log.debug("{} : params: name : {}", logId, name);

        try {
            Optional<Asset> entity = assetRepository.findByName(name, id);
            Optional<AssetOutView> res = modelMapper.map(entity, new TypeToken<Optional<AssetOutView>>() {
            }.getType());
            return res;

        } catch (Exception e) {
            log.error("{} : getByName error", logId, e);
            throw e;
        } finally {
            log.info("{} : getByName finished", logId);
        }
    }
}
