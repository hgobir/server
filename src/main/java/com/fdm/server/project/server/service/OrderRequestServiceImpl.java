package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.*;
import com.fdm.server.project.server.dto.TradeRequest;
import com.fdm.server.project.server.exception.types.TradeException;
import com.fdm.server.project.server.repository.OrderRequestRepository;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import com.fdm.server.project.server.trade.TradePosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestRepository orderRequestRepository;
    private final StockService stockService;
    private final ApplicationUserService applicationUserService;
    private final PortfolioService portfolioService;
    private final RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderRequestServiceImpl(OrderRequestRepository orderRequestRepository, StockService stockService, ApplicationUserService applicationUserService, PortfolioService portfolioService, RestTemplate restTemplate) {
        this.orderRequestRepository = orderRequestRepository;
        this.stockService = stockService;
        this.applicationUserService = applicationUserService;
        this.portfolioService = portfolioService;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderRequest> getOrderRequests() {
        return orderRequestRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderRequest> getOrderRequestById(Long orderRequestId) {
        return orderRequestRepository.findById(orderRequestId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderRequest> getOrderRequestsByApplicationUser(Long applicationUserId) {
        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        System.out.println("this is application user retrieved from database " + applicationUser);
        return orderRequestRepository.findOrderRequestsByApplicationUser(applicationUser);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderRequest getOrderRequestByApplicationUserStockNumberOfShares(Long applicationUserId, Long stockId, Long numberOfShares) {
        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        Stock stock = stockService.getStockById(stockId).get();
        return orderRequestRepository.findOrderRequestByApplicationUserAndStockAndNumberOfShares(applicationUser, stock, numberOfShares);
    }

    @Override
    @Transactional
    public OrderRequest saveOrderRequest(OrderRequest orderRequest) {
        return orderRequestRepository.saveAndFlush(orderRequest);
    }

    @Override
    @Transactional
    public synchronized int updateTrade(Long orderRequestId, Trade trade) {
        return orderRequestRepository.updateTrade(orderRequestId, trade);
    }

    @Override
    @Transactional
    public synchronized int updateOrderRequestStatus(Long orderRequestId, OrderRequestStatus orderRequestStatus) {
        return orderRequestRepository.updateOrderRequestStatus(orderRequestId, orderRequestStatus.name());
    }

//   todo: returning void from async method is called fire and forget!!

    @Override
    @Async
//    @Transactional
    public void executeOrder(String type, String symbol, Long numberOfShares, Long applicationUserId) {
//        System.out.println("inside executeOrder method!");
        try {
            TradePosition tradePosition = type.equalsIgnoreCase("buy") ? TradePosition.LONG : TradePosition.SHORT;
            Stock orderedStock = stockService.getStockUsingSymbol(symbol);
            ApplicationUser user = applicationUserService.getOneUser(applicationUserId);
            Double funds = user.getAvailableFunds();
            Double orderCost = orderedStock.getCurrentValue() * numberOfShares;
            OrderRequest potentialOrderRequest = new OrderRequest(OrderRequestStatus.INITIATED.name(), orderedStock, numberOfShares, user, LocalDateTime.now());
            if (type.equalsIgnoreCase("buy")) {
                if (funds < orderCost) {
                    potentialOrderRequest.setOrderStatus(OrderRequestStatus.USER_DOESNT_HAVE_ENOUGH_MONEY.name());
                    saveOrderRequest(potentialOrderRequest);
                    return;
                } else if (numberOfShares > orderedStock.getCurrentVolume()) {
                    potentialOrderRequest.setOrderStatus(OrderRequestStatus.NOT_ENOUGH_AVAILABLE_SHARES_IN_MARKET.name());
                    saveOrderRequest(potentialOrderRequest);
                    return;
                }
            } else if (type.equalsIgnoreCase("sell")) {
                Portfolio potentialPortfolio = portfolioService.getPortfolioDataAboutStockForUser(user.getApplicationUserId(), orderedStock.getStockId());
                if (potentialPortfolio != null) {
                    Long sellableShares = potentialPortfolio.getUnit();
                    if (sellableShares < numberOfShares) {
                        potentialOrderRequest.setOrderStatus(OrderRequestStatus.USER_DOESNT_HAVE_ENOUGH_SELLABLE_SHARES.name());
                        saveOrderRequest(potentialOrderRequest);
                        return;
                    }
                } else {
                    potentialOrderRequest.setOrderStatus(OrderRequestStatus.USER_DOESNT_HAVE_ANY_SELLABLE_SHARES.name());
                    saveOrderRequest(potentialOrderRequest);
                    return;
                }
            }
            logger.info("about to save orderRequest!");
            logger.info(potentialOrderRequest.toString());
            OrderRequest savedOrderRequest = saveOrderRequest(potentialOrderRequest);
            Long orderRequestId = savedOrderRequest.getOrderRequestId();
            TradeRequest tradeRequest =
                    new TradeRequest(UUID.randomUUID().toString(), potentialOrderRequest.getStock().getSymbol(),
                            potentialOrderRequest.getStock().getCurrentValue(), potentialOrderRequest.getNumberOfShares(),
                            potentialOrderRequest.getApplicationUser().getApplicationUserId(), tradePosition.toString(), orderRequestId);

            logger.info("about to post tradeRequest! " + tradeRequest);

//            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject("http://localhost:8081/trade", tradeRequest, TradeRequest.class);

        } catch (Exception e) {
            System.out.println("this is exception " + e.toString());
            throw new TradeException("something has caused trade to not complete - please contact admin - this is stack trace " + e.getMessage(), applicationUserId);
        }

    }
}
