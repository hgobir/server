package com.fdm.server.project.server.service;

import com.fdm.server.project.server.dto.TradeRequest;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.repository.OrderRequestRepository;
import com.fdm.server.project.server.service.*;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import com.fdm.server.project.server.trade.TradePosition;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderRequestServiceTest {

    @Mock
    private OrderRequestRepository orderRequestRepository;
    @Mock
    private StockService stockService;
    @Mock
    private ApplicationUserService applicationUserService;
    @Mock
    private PortfolioService portfolioService;

    @Mock
    private RestTemplate restTemplate;

    private OrderRequestService underTest;

    @BeforeEach
    void setUp() {
        underTest = new OrderRequestServiceImpl(orderRequestRepository, stockService, applicationUserService, portfolioService, restTemplate);
    }


    @Test
    void testGetOrderRequests() {

        underTest.getOrderRequests();

        Mockito.verify(orderRequestRepository).findAll();
    }

    @Test
    void testGetOrderRequestById() {

        underTest.getOrderRequestById(1L);

        Mockito.verify(orderRequestRepository).findById(anyLong());


    }

    @Test
    void testGetOrderRequestsByApplicationUser() {

        underTest.getOrderRequestsByApplicationUser(1L);

        Mockito.verify(applicationUserService).getOneUser(anyLong());
        Mockito.verify(orderRequestRepository).findOrderRequestsByApplicationUser(any());

    }

    @Test
    void testGetOrderRequestByApplicationUserStockNumberOfShares() {

        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);

        Mockito.when(stockService.getStockById(anyLong())).thenReturn(Optional.of(s));

        underTest.getOrderRequestByApplicationUserStockNumberOfShares(1L, 1L, 1L);

        Mockito.verify(applicationUserService).getOneUser(anyLong());
        Mockito.verify(stockService).getStockById(anyLong());

        Mockito.verify(orderRequestRepository).findOrderRequestByApplicationUserAndStockAndNumberOfShares(any(), any(), any());

    }

    @Test
    void testSaveOrderRequest() {
        ApplicationUser a = new ApplicationUser("test","p","name1", Role.USER);

        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);

        OrderRequest o1 =  new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now());

        underTest.saveOrderRequest(o1);

        Mockito.verify(orderRequestRepository).saveAndFlush(any());
    }

    @Test
    void testUpdateTrade() {


        ApplicationUser a = new ApplicationUser("test","p","name1", Role.USER);

        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);

        Trade t =  new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" );
//        tradeRepository.save(t);


        underTest.updateTrade(1L, t);
        Mockito.verify(orderRequestRepository).updateTrade(anyLong(), any());




    }

    @Test
    void testUpdateOrderRequestStatus() {

        underTest.updateOrderRequestStatus(1L, OrderRequestStatus.SUCCESSFULLY_COMPLETED);

        Mockito.verify(orderRequestRepository).updateOrderRequestStatus(anyLong(), anyString());
    }

    @Test
    void testExecuteOrder() {

        ApplicationUser a = new ApplicationUser("test","p","name1@mail.com", "firstname", "lastname", 5000.0,Role.USER);
        a.setApplicationUserId(1L);

        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);

        OrderRequest o1 =  new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now());
        o1.setOrderRequestId(1L);

        Mockito.when(stockService.getStockUsingSymbol(anyString())).thenReturn(s);
        Mockito.when(applicationUserService.getOneUser(anyLong())).thenReturn(a);
        Mockito.when(underTest.saveOrderRequest(any())).thenReturn(o1);

        underTest.executeOrder("buy", "TEST", 1L, 1L);

        Mockito.verify(stockService).getStockUsingSymbol(anyString());
        Mockito.verify(applicationUserService).getOneUser(anyLong());
        Mockito.verify(restTemplate).postForObject(anyString(), any(), any());

    }
}