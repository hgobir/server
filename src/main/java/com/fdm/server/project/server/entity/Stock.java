package com.fdm.server.project.server.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Stock")
@Table(
        name = "stock"
)
public class Stock {

    @Id
    @Column(
            name = "stock_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long stockId;

    @Column(
            name = "current_value",
            columnDefinition = "double precision"
    )
    private Double currentValue;

    @Column(
            name = "current_volume",
            columnDefinition = "bigint"
    )
    private Long currentVolume;

    @Column(
            name = "gains",
            columnDefinition = "double precision"
    )
    private Double gains;

    @Column(
            name = "open",
            columnDefinition = "double precision"
    )
    private Double open;

    @Column(
            name = "close",
            columnDefinition = "double precision"
    )
    private Double close;

    @Column(
            name = "symbol",
            nullable = false,
            columnDefinition = "text"
    )
    private String symbol;

    @Column(
            name = "last_traded",
            columnDefinition = "text"
    )
    private String lastTraded;

    public Stock() {
    }

    public Stock(Long stockId,
            Double currentValue,
            Long currentVolume,
            String symbol,
            Double gains,
            Double open,
            Double close
    ) {
        this.stockId = stockId;
        this.currentValue = currentValue;
        this.currentVolume = currentVolume;
        this.symbol = symbol;
        this.gains = gains;
        this.open = open;
        this.close = close;
    }


    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) { this.stockId = stockId; }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public Long getCurrentVolume() { return currentVolume; }

    public void setCurrentVolume(long currentVolume) {
        this.currentVolume = currentVolume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getGains() {
        return gains;
    }

    public void setGains(double gains) {
        this.gains = gains;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public String getLastTraded() { return lastTraded; }

    public void setLastTraded(String lastTraded) { this.lastTraded = lastTraded; }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId=" + stockId +
                ", currentValue=" + currentValue +
                ", currentVolume=" + currentVolume +
                ", symbol='" + symbol + '\'' +
                ", gains=" + gains +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
