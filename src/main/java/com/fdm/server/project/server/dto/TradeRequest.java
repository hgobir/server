package com.fdm.server.project.server.dto;

import java.io.Serializable;

public class TradeRequest implements Serializable {

    private String tradeId;
    private String stockSymbol;
    private double price;
    private long volume;
    private long userId;
    private String direction;
    private long orderRequestId;

    public TradeRequest() {
    }

    public TradeRequest(String tradeId, String stockSymbol, double price, long volume, long userId, String direction, long orderRequestId ) {
        this.tradeId = tradeId;
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.volume = volume;
        this.userId = userId;
        this.direction = direction;
        this.orderRequestId = orderRequestId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(long orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    @Override
    public String toString() {
        return "TradeRequest{" +
                "tradeId='" + tradeId + '\'' +
                ", stockSymbol='" + stockSymbol + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", userId=" + userId +
                ", direction='" + direction + '\'' +
                ", orderRequestId=" + orderRequestId +
                '}';
    }
}
