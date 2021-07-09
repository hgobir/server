package com.fdm.server.project.server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fdm.server.project.server.trade.TradePosition;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Trade")
@Table(
        name = "trade"
)
//todo - need to think about how data is going to be structured - should i create mapping that aggregates trades into list or have 1 to 1 mapping?
public class Trade {

    @Id
    @SequenceGenerator(
            name = "trade_sequence",
            sequenceName = "trade_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trade_sequence"
    )
    @Column(
            name = "trade_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long tradeId;

    @Column(
            name = "trade_position"
    )
    @Enumerated(EnumType.STRING)
    private TradePosition tradePosition;

    @OneToOne(
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "application_user_id"
    )
    private ApplicationUser applicationUser;

    @OneToOne(
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
            name = "stock_id"
    )
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
    private Stock stock;

    @Column(
            name = "stock_price_during_trade"
    )
    private Double stockPriceDuringTrade;

    @Column(
            name = "total_cost_of_trade",
            columnDefinition = "double precision"
    )
    private Double totalCostOfTrade;

    @Column(
            name = "trade_volume",
            columnDefinition = "bigint"
    )
    private Long tradeVolume;


    @Column(
            name = "trade_execution_time",
            columnDefinition = "text"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private String tradeExecutionTime;

    @Column(
            name = "trade_execution_reference"
    )
    private String tradeExecutionReference;

    public Trade() {
    }

    public Trade(TradePosition tradePosition,
                 ApplicationUser applicationUser,
                 Stock stock,
                 Double stockPriceDuringTrade,
                 Double totalCostOfTrade,
                 Long tradeVolume,
                 String tradeExecutionTime,
                 String tradeExecutionReference) {
        this.tradeExecutionReference = tradeExecutionReference;
        this.tradePosition = tradePosition;
        this.applicationUser = applicationUser;
        this.stock = stock;
        this.stockPriceDuringTrade = stockPriceDuringTrade;
        this.totalCostOfTrade = totalCostOfTrade;
        this.tradeVolume = tradeVolume;
        this.tradeExecutionTime = tradeExecutionTime;
    }


    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public TradePosition getPosition() {
        return tradePosition;
    }

    public void setPosition(TradePosition tradePosition) {
        this.tradePosition = tradePosition;
    }


    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getStockPriceDuringTrade() {
        return stockPriceDuringTrade;
    }

    public void setStockPriceDuringTrade(Double stockPriceDuringTrade) {
        this.stockPriceDuringTrade = stockPriceDuringTrade;
    }

    public Double getTotalCostOfTrade() {
        return totalCostOfTrade;
    }

    public void setTotalCostOfTrade(Double totalCostOfTrade) {
        this.totalCostOfTrade = totalCostOfTrade;
    }

    public Long getTradeVolume() {
        return tradeVolume;
    }

    public void setTradeVolume(Long tradeVolume) {
        this.tradeVolume = tradeVolume;
    }

    public String getTradeExecutionTime() {
        return tradeExecutionTime;
    }

    public void setTradeExecutionTime(String tradeExecutionTime) {
        this.tradeExecutionTime = tradeExecutionTime;
    }

    public TradePosition getTradePosition() {
        return tradePosition;
    }

    public void setTradePosition(TradePosition tradePosition) {
        this.tradePosition = tradePosition;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getTradeExecutionReference() {
        return tradeExecutionReference;
    }

    public void setTradeExecutionReference(String tradeExecutionReference) {
        this.tradeExecutionReference = tradeExecutionReference;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", tradePosition=" + tradePosition +
                ", applicationUser=" + applicationUser +
                ", stock=" + stock +
                ", stockPriceDuringTrade=" + stockPriceDuringTrade +
                ", totalCostOfTrade=" + totalCostOfTrade +
                ", tradeVolume=" + tradeVolume +
                ", tradeExecutionTime=" + tradeExecutionTime +
                ", tradeExecutionReference='" + tradeExecutionReference + '\'' +
                '}';
    }
}
