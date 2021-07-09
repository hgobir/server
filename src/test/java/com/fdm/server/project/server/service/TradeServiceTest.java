package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.repository.TradeRepository;
import com.fdm.server.project.server.service.ApplicationUserService;
import com.fdm.server.project.server.service.TradeService;
import com.fdm.server.project.server.service.TradeServiceImpl;
import com.fdm.server.project.server.trade.TradePosition;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private ApplicationUserService applicationUserService;

    private TradeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TradeServiceImpl(tradeRepository, applicationUserService);
    }

    @Test
    void testGetTradesForUser() {

        ApplicationUser a =  new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);
        Mockito.when(tradeRepository.getTradesByApplicationUser(any())).thenReturn(anyList());
        underTest.getTradesForUser(a);
        Mockito.verify(tradeRepository).getTradesByApplicationUser(any());
    }

    @Test
    void testSaveTrade() {

        ApplicationUser a = new ApplicationUser("test","p","name1", Role.USER);
//        applicationUserRepository.save(a);
        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
//        stockRepository.save(s);
        Trade t =  new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" );
        underTest.saveTrade(t);
        Mockito.verify(tradeRepository).saveAndFlush(any());
    }

    @Test
    void testGetTradesPerStockForUser() {

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

        Mockito.when(applicationUserService.getOneUser(1L)).thenReturn(a1);
        Mockito.when(tradeRepository.getTradesByApplicationUser(a1)).thenReturn(tradeList);

        underTest.getTradesPerStockForUser(1L, 1L);

        Mockito.verify(applicationUserService).getOneUser(any());
        Mockito.verify(tradeRepository).getTradesByApplicationUser(any());
    }
}