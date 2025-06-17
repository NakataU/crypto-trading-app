package org.example.controllers;

import org.example.exceptions.EntityNotFound;
import org.example.services.UserTransactionsService;
import org.example.views.in.TransactionInView;
import org.example.views.out.TransactionOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user_transactions")
public class UserTransactionsController {

    @Autowired
    private UserTransactionsService userTransactionsService;

    @PostMapping("/buy/{user_id}")
    public ResponseEntity<TransactionOutView> buy(@PathVariable("user_id")Long userId, @Validated @RequestBody TransactionInView body) throws EntityNotFound {
        TransactionOutView res = userTransactionsService.buyAsset(body,userId);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sell/{user_id}")
    public ResponseEntity<TransactionOutView> sell(@PathVariable("user_id")Long userId, @Validated @RequestBody TransactionInView body) throws EntityNotFound {
        TransactionOutView res = userTransactionsService.sellAsset(body,userId);
        return ResponseEntity.ok(res);
    }
}
