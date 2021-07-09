package com.fdm.server.project.server.model;

import com.fdm.server.project.server.entity.Stock;

import java.util.List;

public class StockList {

    private List<Stock> stockList;

    public StockList() {
    }

    public StockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public String toString() {
        return "StockList{" +
                "stockList=" + stockList +
                '}';
    }
}
