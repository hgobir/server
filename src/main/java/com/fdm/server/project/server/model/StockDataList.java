package com.fdm.server.project.server.model;

import java.util.List;

public class StockDataList {


    private List<StockData> stockDataList;

    public StockDataList() {
    }

    public StockDataList(List<StockData> stockDataList) {
        this.stockDataList = stockDataList;
    }

    public List<StockData> getStockDataList() {
        return stockDataList;
    }

    public void setStockDataList(List<StockData> stockDataList) {
        this.stockDataList = stockDataList;
    }

    @Override
    public String toString() {
        return "StockDataList{" +
                "stockDataList=" + stockDataList +
                '}';
    }
}
