package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.trade.OrderRequestStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRequestService {

    List<OrderRequest> getOrderRequests();

    Optional<OrderRequest> getOrderRequestById(Long orderRequestId);

    List<OrderRequest> getOrderRequestsByApplicationUser(Long applicationUserId);

    OrderRequest getOrderRequestByApplicationUserStockNumberOfShares(Long applicationUser, Long stockId, Long numberOfShares);

    OrderRequest saveOrderRequest(OrderRequest orderRequest);

    int updateTrade(Long orderRequestId, Trade trade);

    int updateOrderRequestStatus(Long orderRequestId, OrderRequestStatus orderRequestStatus);

    void executeOrder(String type, String symbol, Long numberOfShares, Long applicationUserId);
}
