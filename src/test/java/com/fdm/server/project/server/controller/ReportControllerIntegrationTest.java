package com.fdm.server.project.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.service.ReportService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReportControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class ReportControllerIntegrationTest {

    private static String URL = "http://localhost:8081/api/v1/server/reports/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;



    @Test
    void testGetReports() throws Exception {


        List<StockData> stockDataList = new ArrayList<>();
        stockDataList.add(new StockData(1L, "TEST", 5.0, 100L));
        stockDataList.add(new StockData(2L, "TEST", 5.0, 100L));
        stockDataList.add(new StockData(3L, "TEST", 5.0, 100L));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        Mockito.when(reportService.createReport(anyLong(), anyString(), anyString(), any())).thenReturn(true);

        String requestJson=ow.writeValueAsString(stockDataList );

        String finalUrl = URL + 1L + "/stock/xml";
        System.out.println("this is final url " + finalUrl);
        RequestBuilder request = MockMvcRequestBuilders.post(finalUrl).contentType(APPLICATION_JSON).content(requestJson);


        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals("application/xml", result.getResponse().getContentType());

        Mockito.verify(reportService).createReport(anyLong(), anyString(), anyString(), any());
    }
}