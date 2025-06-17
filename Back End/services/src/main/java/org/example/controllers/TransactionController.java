package org.example.controllers;

import org.example.entities.UserTransactions;
import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.repository.IUserTransactionsRepository;
import org.example.services.TransactionService;
import org.example.views.in.TransactionInView;
import org.example.views.out.TransactionOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/")
    public ResponseEntity<Page<TransactionOutView>> getAll(Pageable pageable){
        Page<TransactionOutView> res = transactionService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TransactionOutView>> getById(@PathVariable("id") Long id){

        Optional<TransactionOutView> res = transactionService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);

    }

    @PostMapping("/add/{user_id}")
    public ResponseEntity<TransactionOutView> addOne(@PathVariable("user_id")Long userId,@Validated @RequestBody TransactionInView body) throws EntityNotFound {
        TransactionOutView res = transactionService.addOne(body,userId);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws EntityNotFound, EntityNotActive {
        transactionService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionOutView> updateOne(@PathVariable("id") Long id,@Validated @RequestBody TransactionInView body) throws EntityNotFound {
        TransactionOutView res = transactionService.updateOne(id,body);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/user_transactions/{id}")
    public ResponseEntity<Page<TransactionOutView>> getAllByUserId(@PathVariable("id")Long id, Pageable pageable){
        Page<TransactionOutView> res = transactionService.getTransactionByUserId(id,pageable);
        return ResponseEntity.ok(res);
    }
}
