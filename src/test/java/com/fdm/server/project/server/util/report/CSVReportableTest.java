package com.fdm.server.project.server.util.report;

import com.fdm.server.project.server.model.PortfolioReportModel;
import com.fdm.server.project.server.model.StockReportModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVReportableTest {

    @Autowired
    private CSVReportable underTest;

    @BeforeEach
    void setUp() {
        underTest = new CSVReportable();
    }


    @Test
    void testCreateStockReport() throws FileNotFoundException {

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