package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.service.StockService;
import com.fdm.server.project.server.util.converter.StockToStockDataConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StockControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
public class StockControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    @MockBean
    private StockToStockDataConverter stockToStockDataConverter;


    private final String URL = "http://localhost:8081/api/v1/server/stocks/";

    @Test
    void testGetStocks() throws Exception {


        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Stock s2 = new Stock(2L,1.0,1L,"TEST",5.0,5.0,10.0);

        List<Stock> stocks = new ArrayList<>();

        stocks.add(s1);
        stocks.add(s2);

        Mockito.when(stockService.getStocks()).thenReturn(stocks);

        RequestBuilder request = MockMvcRequestBuilders.get(URL);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(stockService).getStocks();

    }

    @Test
    void testGetStockData() throws Exception {

        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Stock s2 = new Stock(2L,1.0,1L,"TEST",5.0,5.0,10.0);

        List<Stock> stocks = new ArrayList<>();

        stocks.add(s1);
        stocks.add(s2);

        Mockito.when(stockService.getStocks()).thenReturn(stocks);

        RequestBuilder request = MockMvcRequestBuilders.get(URL + "stockData");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(stockService).getStocks();
        Mockito.verify(stockToStockDataConverter).convert(stocks);
    }

    @Test
    void testGetStock() throws Exception {

        StockData stockData = new StockData(1L, "TEST", 5.0, 100L);


        Mockito.when(stockService.getStock(anyLong())).thenReturn(stockData);

        RequestBuilder request = MockMvcRequestBuilders.get(URL + 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(stockService).getStock(anyLong());
//        Mockito.verify(stockToStockDataConverter).convert(stocks);


    }
}