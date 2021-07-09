package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.model.Transaction;
import com.fdm.server.project.server.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/server/transactions")
public class TransactionController {

        /*
        todo: remember to sort transaction list from descending to ascending!!
     */

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{applicationUserId}")
    public List<Transaction> getTransactions(@PathVariable("applicationUserId") Long applicationUserId) {
        return transactionService.getTransactions(applicationUserId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getCurrentValues")
    public List<Transaction> getUpdateStocksInTransactions(@RequestBody Transaction[] transactions) {
//        System.out.println("this is what transactions passed from front end is!! " +
//                Arrays.deepToString(transactions));
        //        return null;

//        System.out.println("this is what transactions responded from backend is!! " + Arrays.deepToString(transactionList.toArray()));

        return transactionService.updateCurrentValueInTransactions(transactions);
    }
}
