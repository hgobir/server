package com.fdm.server.project.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.Transaction;
import com.fdm.server.project.server.service.TransactionService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class TransactionControllerIntegrationTest {


    private static String URL = "http://localhost:8081/api/v1/server/transactions/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;



    @Test
    void testGetTransactions() throws Exception {

        Mockito.when(transactionService.getTransactions(anyLong())).thenReturn(any());

        RequestBuilder request = MockMvcRequestBuilders.get(URL + 1L );

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(transactionService).getTransactions(anyLong());
    }

    @Test
    void testGetUpdateStocksInTransactions() throws Exception {

        Stock s1 = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Transaction t1 = new Transaction("TEST", "TEST", 8.0, 123.0,5L,5L, 1L,1L);

        Transaction[] transactions = {t1};

        List<Transaction> listOfTransactions = Arrays.asList(transactions);

        Mockito.when(transactionService.updateCurrentValueInTransactions(transactions)).thenReturn(listOfTransactions);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transactions );

        RequestBuilder request = MockMvcRequestBuilders.post(URL + "/getCurrentValues").contentType(APPLICATION_JSON).content(requestJson);


//        RequestBuilder request = MockMvcRequestBuilders.get(URL + 1L );

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(transactionService).updateCurrentValueInTransactions(any());
    }
}