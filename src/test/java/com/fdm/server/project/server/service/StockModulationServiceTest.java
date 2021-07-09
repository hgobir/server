package com.fdm.server.project.server.service;

import com.fdm.server.project.server.controller.StockModulationController;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.Company;
import com.fdm.server.project.server.service.StockModulationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class StockModulationServiceTest {


    @Mock
    private StockModulationController stockModulationController;

    private StockModulationServiceImpl underTest;


    @BeforeEach
    void setUp() {
        underTest = new StockModulationServiceImpl(stockModulationController);
    }

    @Test
    void testGetCompanyName() {

        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Company testCompany = new Company(1L, "name", "desc", "sector", "ceo", "address", 10L, s);
        Mockito.when(stockModulationController.getCompany(any())).thenReturn((testCompany));

        underTest.getCompanyName(1L);
        Mockito.verify(stockModulationController).getCompany(anyLong());
    }

    @Test
    void testGetCompanyNames() {

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        Stock s1 = new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0);

        Company testCompany1 = new Company(1L, "name1", "desc", "sector", "ceo", "address", 10L, s1);

        Stock s2 = new Stock(2L,1.0,1L,"TEST2",5.0,5.0,10.0);

        Company testCompany2 = new Company(2L, "name", "desc", "sector", "ceo", "address", 10L, s2);

        List<Company> companyList = new ArrayList<>();
        companyList.add(testCompany1);
        companyList.add(testCompany2);

        Mockito.when(stockModulationController.getCompanies(any())).thenReturn((companyList));

        underTest.getCompanyNames(ids);

        Mockito.verify(stockModulationController).getCompanies(anyList());
    }
}