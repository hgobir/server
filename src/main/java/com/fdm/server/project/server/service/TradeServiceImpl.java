package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository,
                            ApplicationUserService applicationUserService) {
        this.tradeRepository = tradeRepository;
        this.applicationUserService = applicationUserService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Trade> getTradesForUser(ApplicationUser applicationUser) {
        return tradeRepository.getTradesByApplicationUser(applicationUser);
    }


    @Override
    @Transactional
    public Trade saveTrade(Trade trade) {
        return tradeRepository.saveAndFlush(trade);
    }

    @Override
    @Transactional
    public List<Trade> getTradesPerStockForUser(Long applicationUserId, Long stockId) {
        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        List<Trade> tradeList = tradeRepository.getTradesByApplicationUser(applicationUser);
        List<Trade> foundTrades = tradeList.stream().filter(trade -> trade.getStock().getStockId().equals(stockId)).collect(Collectors.toList());
        return foundTrades.stream().sorted(Comparator.comparingLong(Trade::getTradeId)).collect(Collectors.toList());
    }
}
