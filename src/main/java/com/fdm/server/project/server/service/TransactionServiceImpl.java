package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final PortfolioService portfolioService;
    private final StockService stockService;
    private final TradeService tradeService;
    private final ApplicationUserService applicationUserService;
    private final StockModulationService stockModulationService;

    @Autowired
    public TransactionServiceImpl(PortfolioService portfolioService,
                                  StockService stockService,
                                  TradeService tradeService,
                                  ApplicationUserService applicationUserService,
                                  StockModulationService stockModulationService) {
        this.portfolioService = portfolioService;
        this.stockService = stockService;
        this.tradeService = tradeService;
        this.applicationUserService = applicationUserService;
        this.stockModulationService = stockModulationService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactions(Long applicationUserId) {

        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        List<Trade> tradeList = tradeService.getTradesForUser(applicationUser);
        Set<Long> stockIds = tradeList.stream().map(trade -> trade.getStock().getStockId()).collect(Collectors.toSet());

        return stockIds.stream().map(stockId -> {
            Long totalTransactions = getTotalNumberPerStock(stockId, tradeList);
            Stock stock = stockService.getStockById(stockId).get();
            Portfolio portfolio = portfolioService.getPortfolioDataAboutStockForUser(applicationUserId, stockId);
            String companyName = stockModulationService.getCompanyName(stockId);
             return new Transaction(stock.getSymbol(),
                                    companyName,
                                    stock.getCurrentValue(),
                                    portfolio.getLastPerShareTradedPrice(),
                                    totalTransactions,
                                    portfolio.getUnit(),
                                    stockId,
                                    portfolio.getPortfolioId());
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> updateCurrentValueInTransactions(Transaction[] arrayOfTransactions) {

        List<Transaction> listOfTransactions = Arrays.asList(arrayOfTransactions);
        listOfTransactions.forEach(transaction -> {
            Stock stock = stockService.getStockById(transaction.getStockId()).get();

//            System.out.println("this is what name of company for this transaction is " + transaction.getCompany());
//            System.out.println("this is what most up to date stock price is " + stock.getCurrentVolume());
            transaction.setCurrentValue(stock.getCurrentValue());
        });
        return listOfTransactions;
    }

    private Long getTotalNumberPerStock(Long stockId, List<Trade> tradeList) {
        return tradeList.stream().filter(trade -> trade.getStock().getStockId().equals(stockId)).count();
    }
}
