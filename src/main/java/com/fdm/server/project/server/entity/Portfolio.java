package com.fdm.server.project.server.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Portfolio")
@Table(
        name = "portfolio"
)
public class Portfolio {

    @Id
    @SequenceGenerator(
            name = "portfolio_sequence",
            sequenceName = "portfolio_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "portfolio_sequence"
    )
    @Column(
            name = "portfolio_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long portfolioId;

    @OneToOne
    @JoinColumn(
            name = "stock_id"
    )
    private Stock stock;

    @Column(
            name = "unit",
            columnDefinition = "bigint",
            nullable = false
    )
    private Long unit;

    @Column(
            name = "total_invested",
            columnDefinition = "double precision",
            nullable = false
    )
    private Double totalInvested;

    @ManyToOne
    @JoinColumn(
            name = "application_user_id",
            nullable = false

    )
    private ApplicationUser applicationUser;

    @Column(
            name = "last_time_traded",
            columnDefinition = "timestamp",
            nullable = false
    )
    private LocalDateTime lastTimeTraded;

    @Column(
            name = "last_per_share_traded_price",
            columnDefinition = "double precision",
            nullable = false
    )
    private Double lastPerShareTradedPrice;

    public Portfolio() {
    }

    public Portfolio(Stock stock,
                     Long unit,
                     Double totalInvested,
                     ApplicationUser applicationUser,
                     LocalDateTime lastTimeTraded,
                     Double lastPerShareTradedPrice) {
        this.stock = stock;
        this.unit = unit;
        this.totalInvested = totalInvested;
        this.applicationUser = applicationUser;
        this.lastTimeTraded = lastTimeTraded;
        this.lastPerShareTradedPrice = lastPerShareTradedPrice;
    }

    public Portfolio(Long portfolioId, Stock stock, Long unit, Double totalInvested, ApplicationUser applicationUser, LocalDateTime lastTimeTraded, Double lastPerShareTradedPrice) {
        this.portfolioId = portfolioId;
        this.stock = stock;
        this.unit = unit;
        this.totalInvested = totalInvested;
        this.applicationUser = applicationUser;
        this.lastTimeTraded = lastTimeTraded;
        this.lastPerShareTradedPrice = lastPerShareTradedPrice;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Double getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(Double totalInvested) {
        this.totalInvested = totalInvested;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public LocalDateTime getLastTimeTraded() {
        return lastTimeTraded;
    }

    public void setLastTimeTraded(LocalDateTime lastTimeTraded) {
        this.lastTimeTraded = lastTimeTraded;
    }

    public Double getLastPerShareTradedPrice() {
        return lastPerShareTradedPrice;
    }

    public void setLastPerShareTradedPrice(Double lastPerShareTradedPrice) {
        this.lastPerShareTradedPrice = lastPerShareTradedPrice;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId=" + portfolioId +
                ", stock=" + stock +
                ", unit=" + unit +
                ", totalInvested=" + totalInvested +
                ", applicationUser=" + applicationUser +
                ", lastTimeTraded=" + lastTimeTraded +
                ", lastPerShareTradedPrice=" + lastPerShareTradedPrice +
                '}';
    }
}
