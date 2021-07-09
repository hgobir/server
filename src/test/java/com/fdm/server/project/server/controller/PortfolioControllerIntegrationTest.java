package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.service.PortfolioService;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(SpringExtension.class)
@WebMvcTest(PortfolioControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class PortfolioControllerIntegrationTest {

    private static String URL = "http://localhost:8081/api/v1/server/portfolio/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;


    @Test
    void testGetPortfolioForStock() throws Exception {

        ApplicationUser a1 = new ApplicationUser("test1","p1","name1", Role.USER);
        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Portfolio p1 =  new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0);



        Mockito.when(portfolioService.getPortfolioDataAboutStockForUser(anyLong(), anyLong())).thenReturn(p1);

        RequestBuilder request = MockMvcRequestBuilders.get(URL + 1L + "/" + 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(portfolioService).getPortfolioDataAboutStockForUser(anyLong(), anyLong());
    }

    @Test
    void testGetSpecificPortfolioForStock() throws Exception {

        ApplicationUser a1 = new ApplicationUser("test1","p1","name1", Role.USER);
        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Portfolio p1 =  new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0);

        Mockito.when(portfolioService.getSpecificPortfolio(anyLong())).thenReturn(p1);

        RequestBuilder request = MockMvcRequestBuilders.get(URL + "specific/" + 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(portfolioService).getSpecificPortfolio(anyLong());

    }

    @Test
    void testGetPortfolios() throws Exception {

        ApplicationUser a1 = new ApplicationUser("test1","p1","name1", Role.USER);
        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Portfolio p1 =  new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0);

        List<Portfolio> test = new ArrayList<>();
        test.add(p1);

        Mockito.when(portfolioService.getPortfolioDataForUser(anyLong())).thenReturn(test);

        RequestBuilder request = MockMvcRequestBuilders.get(URL + 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(portfolioService).getPortfolioDataForUser(anyLong());
    }
}