package com.fdm.server.project.server.service;

import com.fdm.server.project.server.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions(Long applicationUserId);

    List<Transaction> updateCurrentValueInTransactions(Transaction[] transactionList);
}
