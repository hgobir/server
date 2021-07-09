package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.model.Transaction;
import com.fdm.server.project.server.service.*;
import com.fdm.server.project.server.trade.TradePosition;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private PortfolioService portfolioService;
    @Mock
    private StockService stockService;
    @Mock
    private TradeService tradeService;
    @Mock
    private ApplicationUserService applicationUserService;
    @Mock
    private StockModulationService stockModulationService;

    private TransactionService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TransactionServiceImpl(portfolioService,stockService,tradeService,applicationUserService,stockModulationService);

    }

    @Test
    void testGetTransactions() {

        ApplicationUser a1 = new ApplicationUser("test","p","name1", Role.USER);
        a1.setApplicationUserId(1L);

        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Trade t1 =  new Trade(TradePosition.LONG,  a1, s1, 1.0, 1.0, 1L, "test-time","test-ref" );
        t1.setTradeId(1L);
        Trade t2 =  new Trade(TradePosition.LONG,  a1, s1, 1.0, 1.0, 1L, "test-time","test-ref" );
        t2.setTradeId(2L);
        Trade t3 =  new Trade(TradePosition.SHORT,  a1, s1, 1.0, 1.0, 1L, "test-time","test-ref" );
        t3.setTradeId(3L);
        List<Trade> tradeList = new ArrayList<>();

        tradeList.add(t1);
        tradeList.add(t2);
        tradeList.add(t3);

        Portfolio p1 =  new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0);
        p1.setPortfolioId(1L);

        Mockito.when(applicationUserService.getOneUser(1L)).thenReturn(a1);
        Mockito.when(tradeService.getTradesForUser(a1)).thenReturn(tradeList);
        Mockito.when(stockService.getStockById(any())).thenReturn(java.util.Optional.of(s1));
        Mockito.when(portfolioService.getPortfolioDataAboutStockForUser(anyLong(), anyLong())).thenReturn(p1);
        Mockito.when(stockModulationService.getCompanyName(anyLong())).thenReturn("TEST NAME");


        underTest.getTransactions(1L);

        Mockito.verify(applicationUserService).getOneUser(any());
        Mockito.verify(tradeService).getTradesForUser(any());
        Mockito.verify(stockService).getStockById(anyLong());
        Mockito.verify(portfolioService).getPortfolioDataAboutStockForUser(anyLong(), anyLong());
        Mockito.verify(stockModulationService).getCompanyName(anyLong());

    }

    @Test
    void testUpdateCurrentValueInTransactions() {

        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Transaction t1 = new Transaction("TEST", "TEST", 8.0, 123.0,5L,5L, 1L,1L);

        Transaction[] transactions = {t1};

        Mockito.when(stockService.getStockById(any())).thenReturn(java.util.Optional.of(s1));
        underTest.updateCurrentValueInTransactions(transactions);

        Mockito.verify(stockService).getStockById(anyLong());
    }
}