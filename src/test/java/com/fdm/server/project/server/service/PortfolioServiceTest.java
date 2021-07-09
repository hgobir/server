package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.repository.PortfolioRepository;
import com.fdm.server.project.server.service.ApplicationUserService;
import com.fdm.server.project.server.service.PortfolioServiceImpl;
import com.fdm.server.project.server.service.StockService;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {


    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private ApplicationUserService applicationUserService;

    @Mock
    private StockService stockService;

    private PortfolioServiceImpl underTest;


    @BeforeEach
    void setUp() {
        underTest = new PortfolioServiceImpl(portfolioRepository, applicationUserService, stockService);
    }

    @Test
    void testGetPortfolioDataAboutStockForUser() {

        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);

        Mockito.when(stockService.getStockById(anyLong())).thenReturn(Optional.of(s));

        underTest.getPortfolioDataAboutStockForUser(1L, 2L);

        Mockito.verify(applicationUserService).getOneUser(anyLong());
        Mockito.verify(stockService).getStockById(anyLong());
        Mockito.verify(portfolioRepository).findPortfolioByApplicationUserAndStock(any(), any());
    }

    @Test
    void testSavePortfolio() {

        ApplicationUser a1 = new ApplicationUser("test1","p1","name1", Role.USER);
        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Portfolio p1 =  new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0);
        underTest.savePortfolio(p1);

        Mockito.verify(portfolioRepository).save(any());
    }

    @Test
    void testGetPortfolioDataForUser() {

        underTest.getPortfolioDataForUser(1L);

        Mockito.verify(applicationUserService).getOneUser(anyLong());
        Mockito.verify(portfolioRepository).findPortfolioByApplicationUser(any());
    }


    @Test
    void testGetSpecificPortfolio() {

        underTest.getSpecificPortfolio(1L);
        Mockito.verify(portfolioRepository).findPortfolioByPortfolioId(anyLong());
    }
}