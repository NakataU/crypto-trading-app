package org.example.services.impl;

import org.example.entities.User;
import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.repository.IAssetRepository;
import org.example.repository.ITransactionRepository;
import org.example.repository.IUserRepository;
import org.example.services.UserService;
import org.example.views.in.UserInView;
import org.example.views.out.UserOutView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private static final BigDecimal BALANCE = BigDecimal.valueOf(10000);
    private static final BigDecimal BALANCE_IN_ASSETS = BigDecimal.valueOf(0);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAssetRepository assetRepository;

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<UserOutView> getAll(Pageable pageable) {
        String logId = UUID.randomUUID().toString();

        log.info("{} : getAll start", logId);
        try {

            Page<User> users = userRepository.findAllActive(pageable);
            log.debug("{} : retrieved {} users", logId, users.getSize());

            List<UserOutView> list = modelMapper.map(users.getContent(),
                    new TypeToken<List<UserOutView>>() {
                    }.getType());
            Page<UserOutView> res = new PageImpl<>(list, pageable, users.getTotalElements());

            log.debug("{} : mapped to {} UserOutView", logId, res.getSize());
            return res;
        } catch (Exception e) {
            log.error("{} getAll error", logId, e);
            throw e;
        } finally {
            log.info("{} : getAll finished", logId);
        }
    }

    @Override
    public Optional<UserOutView> getById(Long id) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Optional<User> entity = userRepository.findByIdIsActive(id);
            Optional<UserOutView> res = modelMapper.map(entity, new TypeToken<Optional<UserOutView>>() {
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
    public UserOutView addOne(UserInView user) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: body : {}", logId, user);

        try {
            User entity = modelMapper.map(user, new TypeToken<User>() {
            }.getType());
            entity.setBalance(BALANCE_IN_ASSETS);
            entity.setFreeBalance(BALANCE);
            entity.setIsActive(true);
            entity = userRepository.save(entity);
            UserOutView res = modelMapper.map(entity, new TypeToken<UserOutView>() {
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
    public UserOutView updateOne(Long id, UserInView user) throws EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : updateOne start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            User entity = userRepository.findByIdIsActive(id).orElseThrow(() -> new EntityNotFound());
            User toSave = modelMapper.map(user, new TypeToken<User>() {
            }.getType());
            toSave.setId(id);
            toSave.setIsActive(entity.getIsActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            toSave = userRepository.save(toSave);

            UserOutView res = modelMapper.map(toSave, new TypeToken<UserOutView>() {
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
        log.info("{} : deleteById start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            User entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFound());
            if (!entity.getIsActive()) {
                throw new EntityNotActive();
            }
            entity.setIsActive(false);
            userRepository.save(entity);

        } catch (Exception e) {
            log.error("{} : deleteById error", logId, e);
            throw e;
        } finally {
            log.info("{} : deleteById finished", logId);
        }
    }

    @Override
    public UserOutView resetUser(Long id) throws EntityNotActive, EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : resetUser start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {

            this.assetRepository.deactivateAssetsByUserId(id);

            this.transactionRepository.deactivateTransactionsByUserId(id);

            User entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFound());
            entity.setBalance(BALANCE_IN_ASSETS);
            entity.setFreeBalance(BALANCE);
            entity.setIsActive(true);
            entity = userRepository.save(entity);
            UserOutView res = modelMapper.map(entity, new TypeToken<UserOutView>() {
            }.getType());
            return res;

        } catch (Exception e) {
            log.error("{} : resetUser error", logId, e);
            throw e;
        } finally {
            log.info("{} : resetUser finished", logId);
        }    }
}
