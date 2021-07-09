package com.fdm.server.project.server.util.report;

import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.PortfolioReportModel;
import com.fdm.server.project.server.model.StockReportModel;
import com.fdm.server.project.server.util.converter.StockToStockDataConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class XMLReportableTest {

    @Autowired
    private XMLReportable underTest;

    @BeforeEach
    void setUp() {
        underTest = new XMLReportable();
    }

    @Test
    void testCreateStockReport() {

        List<StockReportModel> stockReportModels = new ArrayList<>();
        stockReportModels.add( new StockReportModel("TEST1", "RANDOM COMPANY1",100.0,1000,100));
        stockReportModels.add( new StockReportModel("TEST2", "RANDOM COMPANY2",100.0,1000,100));

        boolean result = underTest.createStockReport(stockReportModels);

        assertTrue(result);

    }

    @Test
    void testCreatePortfolioReport() throws IOException {

        List<PortfolioReportModel> portfolioReportModels = new ArrayList<>();
        portfolioReportModels.add(new PortfolioReportModel("test1","testname",10000.0,5L,"test-date", 1000.0));
        portfolioReportModels.add(new PortfolioReportModel("test2","testname",10000.0,5L,"test-date", 1000.0));

        boolean result = underTest.createPortfolioReport(portfolioReportModels);

        assertTrue(result);
    }
}