package com.fdm.server.project.server.model;

public class StockData {

    private Long stockDataId;
    private String symbol;
    private Double value;
    private Long volume;

    public StockData() {
    }

    public StockData(Long stockDataId, String symbol, Double value, Long volume) {
        this.stockDataId = stockDataId;
        this.symbol = symbol;
        this.value = value;
        this.volume = volume;
    }

    public Long getStockDataId() {
        return stockDataId;
    }

    public void setStockDataId(Long stockDataId) {
        this.stockDataId = stockDataId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "stockDataId=" + stockDataId +
                ", symbol='" + symbol + '\'' +
                ", value=" + value +
                ", volume=" + volume +
                '}';
    }
}
