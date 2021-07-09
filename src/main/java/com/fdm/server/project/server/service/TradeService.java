package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Trade;

import java.util.List;
import java.util.Optional;

public interface TradeService {

    List<Trade> getTradesForUser(ApplicationUser applicationUserId);

    Trade saveTrade(Trade trade);

    List<Trade> getTradesPerStockForUser(Long applicationUserId, Long stockId);
}
