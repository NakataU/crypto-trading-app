package org.example.services.impl;

import org.example.entities.Asset;
import org.example.entities.Transaction;
import org.example.entities.User;
import org.example.exceptions.EntityNotFound;
import org.example.repository.IAssetRepository;
import org.example.repository.ITransactionRepository;
import org.example.repository.IUserRepository;
import org.example.repository.IUserTransactionsRepository;
import org.example.services.AssetService;
import org.example.services.UserTransactionsService;
import org.example.views.in.AssetInView;
import org.example.views.in.TransactionInView;
import org.example.views.out.TransactionOutView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserTransactionsServiceImpl implements UserTransactionsService {
    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private IUserTransactionsRepository userTransactionsRepository;

    @Autowired
    private IAssetRepository assetRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionOutView buyAsset(TransactionInView transaction, Long userId) throws EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: body : {}", logId, transaction);

        try {
            Transaction entity = modelMapper.map(transaction, new TypeToken<Transaction>() {
            }.getType());
            System.out.println(entity);
            entity.setIsActive(true);
            entity = transactionRepository.save(entity);

            Optional<Asset> availableAsset = assetRepository.findByName(entity.getAssetName(), userId);

            if (availableAsset.isPresent()) {
                Asset currentAsset = availableAsset.get();
                currentAsset.setPrice(entity.getPrice().add(currentAsset.getPrice()));
                currentAsset.setQuantity(entity.getQuantity().add(currentAsset.getQuantity()));
                currentAsset.setUserId(userId);
                AssetInView asset = modelMapper.map(currentAsset, new TypeToken<AssetInView>() {
                }.getType());
                assetService.updateOne(currentAsset.getId(), asset);
            } else {
                Asset transactionAsset = new Asset();
                transactionAsset.setName(entity.getAssetName());
                transactionAsset.setSymbol(entity.getAssetSymbol());
                transactionAsset.setQuantity(entity.getQuantity());
                transactionAsset.setPrice(entity.getPrice());
                transactionAsset.setUserId(userId);
                transactionAsset.setIsActive(true);
                assetRepository.save(transactionAsset);
            }

            //Creating userTransaction
            org.example.entities.UserTransactions userTransactions = new org.example.entities.UserTransactions();
            userTransactions.setUserId(userId);
            userTransactions.setTransactionId(entity.getId());
            userTransactions.setIsActive(true);
            userTransactionsRepository.save(userTransactions);

            User current = userRepository.findByIdIsActive(userId).get();
            current.setFreeBalance(current.getFreeBalance().subtract(transaction.getPrice()));
            current.setBalance(current.getBalance().add(transaction.getPrice()));
            userRepository.save(current);

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
    public TransactionOutView sellAsset(TransactionInView transaction, Long userId) throws EntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addOne start", logId);
        log.debug("{} : params: body : {}", logId, transaction);

        try {
            Transaction entity = modelMapper.map(transaction, new TypeToken<Transaction>() {
            }.getType());
            System.out.println(entity);
            entity.setIsActive(true);
            entity = transactionRepository.save(entity);

            Optional<Asset> availableAsset = assetRepository.findByName(entity.getAssetName(), userId);

            if (availableAsset.isPresent()) {
                Asset currentAsset = availableAsset.get();
                currentAsset.setPrice(currentAsset.getPrice().subtract(entity.getPrice()));
                currentAsset.setQuantity(currentAsset.getQuantity().subtract(entity.getQuantity()));
                currentAsset.setUserId(userId);
                AssetInView asset = modelMapper.map(currentAsset, new TypeToken<AssetInView>() {
                }.getType());
                assetService.updateOne(currentAsset.getId(), asset);
            } else {
                Asset transactionAsset = new Asset();
                transactionAsset.setName(entity.getAssetName());
                transactionAsset.setQuantity(entity.getQuantity());
                transactionAsset.setPrice(entity.getPrice());
                transactionAsset.setSymbol(entity.getAssetSymbol());
                transactionAsset.setUserId(userId);
                transactionAsset.setIsActive(true);
                assetRepository.save(transactionAsset);
            }

            //Creating userTransaction
            org.example.entities.UserTransactions userTransactions = new org.example.entities.UserTransactions();
            userTransactions.setUserId(userId);
            userTransactions.setTransactionId(entity.getId());
            userTransactions.setIsActive(true);
            userTransactionsRepository.save(userTransactions);

            User current = userRepository.findByIdIsActive(userId).get();
            current.setFreeBalance(current.getFreeBalance().add(transaction.getPrice()));
            current.setBalance(current.getBalance().subtract(transaction.getPrice()));
            userRepository.save(current);

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
}
