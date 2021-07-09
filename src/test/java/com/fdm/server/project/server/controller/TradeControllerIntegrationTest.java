package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.service.TradeService;
import com.fdm.server.project.server.service.TradeServiceImpl;
import com.fdm.server.project.server.trade.TradePosition;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.RequestDispatcher;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class TradeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    private final String URL = "http://localhost:8081/api/v1/server/trade/";

    @Test
    void testGetTradesPerStock() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get(URL + 1L + "/" + 1L);

        ApplicationUser a = new ApplicationUser("test","p","name1", Role.USER);
        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Trade t1 =  new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" );
        Trade t2 =  new Trade(TradePosition.SHORT, a, s, 5.0, 5.0, 5L, "test-time2","test-ref2");

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(t1);
        tradeList.add(t2);

        Mockito.when(tradeService.getTradesPerStockForUser(anyLong(), anyLong())).thenReturn(tradeList);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        Mockito.verify(tradeService).getTradesPerStockForUser(anyLong(), anyLong());
    }
}