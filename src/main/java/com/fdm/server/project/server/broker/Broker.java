package com.fdm.server.project.server.broker;

import com.fdm.server.project.server.dto.TradeRequest;
import com.fdm.server.project.server.dto.TradeResponse;
import com.fdm.server.project.server.entity.*;
import com.fdm.server.project.server.service.*;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import com.fdm.server.project.server.trade.TradePosition;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Broker {

    private final ApplicationUserService applicationUserService;
    private final StockService stockService;
    private final PortfolioService portfolioService;
    private final OrderRequestService orderRequestService;
    private final TradeService tradeService;

    @Autowired
    public Broker(ApplicationUserService applicationUserService, StockService stockService, PortfolioService portfolioService, OrderRequestService orderRequestService, TradeService tradeService) {
        this.applicationUserService = applicationUserService;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
        this.orderRequestService = orderRequestService;
        this.tradeService = tradeService;
    }

    @RabbitListener(queues = "#{queue.name}", concurrency = "10")
    public TradeResponse receiveFromQueue(TradeRequest tradeRequest) {

        System.out.println("inside broker!");
        System.out.println("this is tradeRequest from queue " + tradeRequest.toString());

        OrderRequest orderRequest = orderRequestService.getOrderRequestById(tradeRequest.getOrderRequestId()).get();
        //
        orderRequestService.updateOrderRequestStatus(orderRequest.getOrderRequestId(), OrderRequestStatus.PENDING);

        Stock stock = stockService.getStockUsingSymbol(tradeRequest.getStockSymbol());
        Double currentPrice = stock.getCurrentValue();
        Long currentVolume = stock.getCurrentVolume();
        String direction = tradeRequest.getDirection();
        Long unitOfStocks = tradeRequest.getVolume();
        Double tradeCost = currentPrice * unitOfStocks;

        ApplicationUser applicationUser = applicationUserService.getOneUser(tradeRequest.getUserId());

        Double availableFunds = applicationUser.getAvailableFunds();
        LocalDateTime time = LocalDateTime.now();

        Trade trade;

        if(direction.equalsIgnoreCase("LONG")) {

//        todo: logic to deduct money from user

            double newAvailableFunds = availableFunds - tradeCost;

            applicationUserService.updateAvailableFunds(applicationUser.getApplicationUserId(), newAvailableFunds);

//        todo: deduct number of shares from volume

            stock.setCurrentVolume(currentVolume - unitOfStocks);

//        todo: add stock amount to portfolio

            Portfolio potentialPortfolio = portfolioService.getPortfolioDataAboutStockForUser(applicationUser.getApplicationUserId(), stock.getStockId());

            if (potentialPortfolio != null) {

                potentialPortfolio.setUnit(potentialPortfolio.getUnit() + unitOfStocks );
                potentialPortfolio.setTotalInvested(potentialPortfolio.getTotalInvested() + tradeCost);
                potentialPortfolio.setLastTimeTraded(time);

                portfolioService.savePortfolio(potentialPortfolio);

            } else {
                Portfolio newPortfolio = new Portfolio(stock, unitOfStocks, tradeCost, applicationUser, time, stock.getCurrentValue());
                portfolioService.savePortfolio(newPortfolio);
            }

            trade = new Trade(TradePosition.LONG,  applicationUser, stock, currentPrice, tradeCost, unitOfStocks, time.toString(),tradeRequest.getTradeId() );


        } else {

//        todo: logic to add money from user

            double newAvailableFunds = availableFunds + tradeCost;

            applicationUserService.updateAvailableFunds(applicationUser.getApplicationUserId(), newAvailableFunds);

//        todo: add number of shares to volume

            stock.setCurrentVolume(currentVolume + unitOfStocks);

//        todo: remove stock amount to portfolio

            Portfolio portfolio = portfolioService.getPortfolioDataAboutStockForUser(applicationUser.getApplicationUserId(), stock.getStockId());

            portfolio.setUnit(portfolio.getUnit() - unitOfStocks );
            portfolio.setTotalInvested(portfolio.getTotalInvested() - tradeCost);
            portfolio.setLastTimeTraded(LocalDateTime.now());

            portfolioService.savePortfolio(portfolio);

            trade = new Trade(TradePosition.SHORT,  applicationUser, stock, currentPrice, tradeCost, unitOfStocks, time.toString(),tradeRequest.getTradeId() );


        }

        stockService.updateLastTraded(stock.getStockId(), time.toString());
        Trade completedTrade = tradeService.saveTrade(trade);

        return new TradeResponse(tradeRequest.getTradeId(), completedTrade, LocalDateTime.now().toString(), tradeRequest.getUserId(), "COMPLETE");

    }
}
