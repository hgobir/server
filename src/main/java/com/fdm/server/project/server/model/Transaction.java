package com.fdm.server.project.server.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);
    private String company;
    private long transactionId;
    private String symbol;
    private double currentValue;
    private double purchaseValue;
    private long numberOfTransactions;
    private long shareAmount;
    private long stockId;
    private long portfolioId;

    public Transaction() {
    }

    public Transaction(
            String symbol,
            String company,
            double currentValue,
            double purchaseValue,
            long numberOfTransactions,
            long shareAmount,
            long stockId,
            long portfolioId) {
        transactionId = ID_GENERATOR.getAndIncrement();
        this.company = company;
        this.symbol = symbol;
        this.currentValue = currentValue;
        this.purchaseValue = purchaseValue;
        this.numberOfTransactions = numberOfTransactions;
        this.shareAmount = shareAmount;
        this.stockId = stockId;
        this.portfolioId = portfolioId;
    }

    public static AtomicInteger getIdGenerator() {
        return ID_GENERATOR;
    }

    public String getCompany() {
        return company;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(double purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public long getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(long numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public long getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(long shareAmount) {
        this.shareAmount = shareAmount;
    }

    public long getStockId() {
        return stockId;
    }

    public long getPortfolioId() {
        return portfolioId;
    }
}
