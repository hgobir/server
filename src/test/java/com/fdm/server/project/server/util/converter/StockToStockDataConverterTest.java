package com.fdm.server.project.server.util.converter;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.CreditCard;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.CreditCardResponse;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.service.StockService;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StockToStockDataConverterTest {

    @Mock
    private StockService stockService;

    @Autowired
    private StockToStockDataConverter underTest;


    @BeforeEach
    void setUp() {
        underTest = new StockToStockDataConverter(stockService);
    }

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    void testConvert() {

        ApplicationUser a = new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        Stock s1 = new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0);
        Stock s2 = new Stock(2L,1.0,1L,"TEST2",5.0,5.0,10.0);

        List<Stock> stocks = new ArrayList<>();
        stocks.add(s1);
        stocks.add(s2);


        List<StockData> expectedResult = underTest.convert(stocks);


        assertNotNull(expectedResult);

        Object classResult =expectedResult.get(0).getClass();
        assertEquals(StockData.class, classResult);

    }

    @Test
    void testRevert() {

        ApplicationUser a = new ApplicationUser("test", "p", "test@test.com", "test-firstname", "test-lastname", 50000.0, Role.USER);

        List<StockData> stockDataList = new ArrayList<>();
        stockDataList.add(new StockData(1L, "TEST", 5.0, 100L));
        stockDataList.add(new StockData(2L, "TEST", 5.0, 100L));

        Stock s1 = new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0);


        Mockito.when(stockService.getStockUsingSymbol("TEST")).thenReturn((s1));

        List<Stock> expectedResult = underTest.revert(stockDataList);


        Mockito.verify(stockService, times(2)).getStockUsingSymbol(anyString());

        assertNotNull(expectedResult);

        Object classResult =expectedResult.get(0).getClass();
        assertEquals(Stock.class, classResult);


    }
}