package com.fdm.server.project.server.service;

import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.service.PortfolioService;
import com.fdm.server.project.server.service.ReportService;
import com.fdm.server.project.server.service.ReportServiceImpl;
import com.fdm.server.project.server.service.StockModulationService;
import com.fdm.server.project.server.util.converter.StockToStockDataConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private StockToStockDataConverter stockToStockDataConverter;
    @Mock
    private PortfolioService portfolioService;
    @Mock
    private StockModulationService stockModulationService;

    private ReportService underTest;


    @BeforeEach
    void setUp() {
        underTest = new ReportServiceImpl(stockToStockDataConverter, portfolioService, stockModulationService);
    }

    @Test
    void testCreateReport() throws IOException, URISyntaxException {

        List<StockData> stockDataList = new ArrayList<>();
        stockDataList.add(new StockData(1L, "TEST", 5.0, 100L));

        Map<Long,String> companyNames = new HashMap<>();
        companyNames.put(1L, "test company");

        Mockito.when(stockModulationService.getCompanyNames(any())).thenReturn((companyNames));

        underTest.createReport(1L, "stock", "csv", stockDataList);

        Mockito.verify(stockModulationService).getCompanyNames(any());

    }
}