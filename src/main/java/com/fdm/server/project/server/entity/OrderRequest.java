package com.fdm.server.project.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fdm.server.project.server.trade.OrderRequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "OrderRequest")
@Table(
        name = "order_request"
)
public class OrderRequest {

    @Id
    @SequenceGenerator(
            name = "order_request_sequence",
            sequenceName = "order_request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_request_sequence"
    )
    @Column(
            name = "order_request_id",
            nullable = false,
            unique = true,
            columnDefinition = "bigint"
    )
    private Long orderRequestId;

    @Column(
            name = "order_request_status",
            columnDefinition = "text"
    )
//    @Enumerated(EnumType.STRING)
    private String orderRequestStatus;

    @OneToOne(
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "trade_id"
    )
    @JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
    private Trade trade;

    @OneToOne
    @JoinColumn(
            name = "stock_id"
    )
    private Stock stock;

    @Column(
            name = "number_of_shares",
            columnDefinition = "bigint"
    )
    private Long numberOfShares;

    @ManyToOne
    @JoinColumn(
            name = "application_user_id"
    )
    private ApplicationUser applicationUser;

    @Column(
            name = "order_placed_time",
            columnDefinition = "timestamp"
    )
    private LocalDateTime orderPlacedTime;

    public OrderRequest() {
    }


    public OrderRequest(String orderRequestStatus,
                        Stock stock,
                        Long numberOfShares,
                        ApplicationUser applicationUser,
                        LocalDateTime orderPlacedTime
    ) {
        this.orderRequestStatus = orderRequestStatus;
        this.stock = stock;
        this.numberOfShares = numberOfShares;
        this.applicationUser = applicationUser;
        this.orderPlacedTime = orderPlacedTime;
    }

    public OrderRequest(String orderRequestStatus,
                        Stock stock,
                        Long numberOfShares,
                        ApplicationUser applicationUser,
                        LocalDateTime orderPlacedTime,
                        Trade trade
    ) {
        this.orderRequestStatus = orderRequestStatus;
        this.stock = stock;
        this.numberOfShares = numberOfShares;
        this.applicationUser = applicationUser;
        this.orderPlacedTime = orderPlacedTime;
        this.trade = trade;
    }



    public Long getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(Long orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public String getOrderStatus() {
        return orderRequestStatus;
    }

    public void setOrderStatus(String orderRequestStatus) {
        this.orderRequestStatus = orderRequestStatus;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Long getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(Long numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public LocalDateTime getOrderPlacedTime() {
        return orderPlacedTime;
    }

    public void setOrderPlacedTime(LocalDateTime orderPlacedTime) {
        this.orderPlacedTime = orderPlacedTime;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderRequestId=" + orderRequestId +
                ", orderRequestStatus=" + orderRequestStatus +
                ", trade=" + trade +
                ", stock=" + stock +
                ", numberOfShares=" + numberOfShares +
                ", applicationUser=" + applicationUser +
                ", orderPlacedTime=" + orderPlacedTime +
                '}';
    }
}
