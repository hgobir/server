package com.fdm.server.project.server.service;


import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.repository.StockRepository;
import com.fdm.server.project.server.service.StockService;
import com.fdm.server.project.server.service.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {


    @Mock
    private StockRepository stockRepository;

    private StockService underTest;


    @BeforeEach
    void setUp() {
        underTest = new StockServiceImpl(stockRepository);
    }



    @Test
    void testGetStocks() {
        underTest.getStocks();

        Mockito.verify(stockRepository).findAll();

    }

    @Test
    void testGetStockById() {
        underTest.getStockById(1L);
        Mockito.verify(stockRepository).findById(anyLong());
    }

    @Test
    void testGetStockBySymbol() {

        underTest.getStockUsingSymbol("TEST");
        Mockito.verify(stockRepository).getStockBySymbol(anyString());

    }


    @Test
    void testSaveModulatedStocks() {

        Stock s1 = new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0);
        Stock s2 = new Stock(2L,1.0,1L,"TEST2",5.0,5.0,10.0);

        List<Stock> stocks = new ArrayList<>();
        stocks.add(s1);
        stocks.add(s2);

        Mockito.when(stockRepository.existsById(anyLong())).thenReturn(true);
        underTest.saveModulatedStocks(stocks);

        Mockito.verify(stockRepository, Mockito.times(2)).existsById(anyLong());
        Mockito.verify(stockRepository, Mockito.times(2)).modulateVolume(anyLong(), anyLong());
        Mockito.verify(stockRepository, Mockito.times(2)).modulateValue(anyLong(), anyDouble());
    }


    @Test
    void testUpdateLastTraded() {

        underTest.updateLastTraded(1L, "test");
        Mockito.verify(stockRepository).updateLastTraded(anyLong(),anyString());
    }


    @Test
    void testGetStock() {

        Stock s1 = new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0);
        Mockito.when(stockRepository.getStockByStockId(anyLong())).thenReturn(s1);
        underTest.getStock(1L);
        Mockito.verify(stockRepository).getStockByStockId(anyLong());
    }






}
