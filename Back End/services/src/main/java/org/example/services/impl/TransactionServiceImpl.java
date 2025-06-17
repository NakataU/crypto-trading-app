package org.example.services.impl;

import jakarta.transaction.UserTransaction;
import org.example.entities.Asset;
import org.example.entities.Transaction;
import org.example.entities.User;
import org.example.entities.UserTransactions;
import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.repository.IAssetRepository;
import org.example.repository.ITransactionRepository;
import org.example.repository.IUserRepository;
import org.example.repository.IUserTransactionsRepository;
import org.example.services.TransactionService;
import org.example.views.in.AssetInView;
import org.example.views.in.TransactionInView;
import org.example.views.in.UserInView;
import org.example.views.out.TransactionOutView;
import org.example.views.out.UserOutView;
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
public class TransactionServiceImpl implements TransactionService {
    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IUserTransactionsRepository  userTransactionsRepository;

    @Autowired
    private IAssetRepository assetRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<TransactionOutView> getAll(Pageable pageable) {
        String logId = UUID.randomUUID().toString();

        log.info("{} : getAll start", logId);
        try {
            Page<Transaction> transactions = transactionRepository.findAllActive(pageable);
            log.debug("{} : retrieved {} transactions", logId, transactions.getSize());

            List<TransactionOutView> list = modelMapper.map(transactions.getContent(),
                    new TypeToken<List<TransactionOutView>>() {
                    }.getType());
            Page<TransactionOutView> res = new PageImpl<>(list, pageable, transactions.getTotalElements());

            log.debug("{} : mapped to {} TransactionOutView", logId, res.getSize());
            return res;
        } catch (Exception e) {
            log.error("{} getAll error", logId, e);
            throw e;
        } finally {
            log.info("{} : getAll finished", logId);
        }
    }

    @Override
    public Optional<TransactionOutView> getById(Long id) {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getById start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Optional<Transaction> entity = transactionRepository.findByIdIsActive(id);
            Optional<TransactionOutView> res = modelMapper.map(entity, new TypeToken<Optional<TransactionOutView>>() {
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
    public TransactionOutView addOne(TransactionInView transaction, Long userId) throws EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: body : {}", logId, transaction);

        try {
            Transaction entity = modelMapper.map(transaction, new TypeToken<Transaction>() {
            }.getType());
            entity.setIsActive(true);
            entity = transactionRepository.save(entity);
            TransactionOutView res = modelMapper.map(entity, new TypeToken<TransactionOutView>() {
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
    public TransactionOutView updateOne(Long id, TransactionInView transaction) throws EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : updateOne start", logId);
        log.debug("{} : params: id : {}", logId, id);

        try {
            Transaction entity = transactionRepository.findByIdIsActive(id).orElseThrow(() -> new EntityNotFound());
            Transaction toSave = modelMapper.map(transaction, new TypeToken<Transaction>() {
            }.getType());
            toSave.setId(id);
            toSave.setIsActive(entity.getIsActive());
            toSave.setCreatedBy(entity.getCreatedBy());
            toSave.setCreatedOn(entity.getCreatedOn());
            toSave = transactionRepository.save(toSave);

            TransactionOutView res = modelMapper.map(toSave, new TypeToken<TransactionOutView>() {
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
            Transaction entity = transactionRepository.findById(id).orElseThrow(() -> new EntityNotFound());
            if (!entity.getIsActive()) {
                throw new EntityNotActive();
            }
            entity.setIsActive(false);
            transactionRepository.save(entity);

        } catch (Exception e) {
            log.error("{} : deleteById error", logId, e);
            throw e;
        } finally {
            log.info("{} : deleteById finished", logId);
        }
    }

    @Override
    public Page<TransactionOutView> getTransactionByUserId(Long userId, Pageable pageable) {
        String logId = UUID.randomUUID().toString();

        log.info("{} : getTransactionByUserId start", logId);
        try {
            Page<Transaction> transactions = transactionRepository.findAllByUserId(pageable,userId);
            log.debug("{} : retrieved {} user transactions", logId, transactions.getSize());

            List<TransactionOutView> list = modelMapper.map(transactions.getContent(),
                    new TypeToken<List<TransactionOutView>>() {
                    }.getType());
            Page<TransactionOutView> res = new PageImpl<>(list, pageable, transactions.getTotalElements());

            log.debug("{} : mapped to {} TransactionOutView", logId, res.getSize());
            return res;
        } catch (Exception e) {
            log.error("{} getTransactionByUserId error", logId, e);
            throw e;
        } finally {
            log.info("{} : getTransactionByUserId finished", logId);
        }
    }
}
