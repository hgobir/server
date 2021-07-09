package com.fdm.server.project.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.*;
import com.fdm.server.project.server.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@ExtendWith(SpringExtension.class)
@WebMvcTest(StockModulationControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class StockModulationControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private TestRestTemplate testRestTemplate;

    private final String URL = "http://localhost:8081/api/v1/server/stockModulation/";

    @BeforeEach
    void setUp() {
        testRestTemplate = new TestRestTemplate();
    }


    @Test
    void testReceiveCurrentStockData() throws Exception {

        List<Stock> stockList = new ArrayList<>();
        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Stock s2 = new Stock(2L,1.0,1L,"TEST",5.0,5.0,10.0);

        StockList stockListObj = new StockList(stockList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson=ow.writeValueAsString(stockListObj );

//        Mockito.when(stockService.saveModulatedStocks(any()));

        RequestBuilder request = MockMvcRequestBuilders.post(URL + "currentStockPrices").contentType(APPLICATION_JSON).content(requestJson);

        MvcResult result = mockMvc.perform(request).andReturn();


        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(stockService).saveModulatedStocks(any());
    }

    @Test
    void testGetCompany() {

//        CompanyList companyList = restTemplate.postForObject(STOCK_MODULATION_URL + "admin/companies/specific", stockIdList, CompanyList.class);


        Company response = testRestTemplate.
                getForObject( "http://localhost:8080/api/v1/stockModulation/" + 1L, Company.class);
        assertNotNull(response);
    }

    @Test
    void testGetCompanies() {

        ListOfIds stockIdList = new ListOfIds();
        List<Long> longList = new ArrayList<Long>();
        longList.add(1L);

        stockIdList.setLongList(longList);

        CompanyList response = testRestTemplate.postForObject( "http://localhost:8080/api/v1/stockModulation/admin/companies/specific", stockIdList, CompanyList.class);;
        assertNotNull(response);
    }
}