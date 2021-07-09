package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.RabbitMqConfiguration;
import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.service.OrderRequestService;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import com.fdm.server.project.server.trade.TradePosition;
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
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderRequestControllerIntegrationTest.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {RabbitMqConfiguration.class, SecurityConfig.class})
class OrderRequestControllerIntegrationTest {

    private static String URL = "http://localhost:8081/api/v1/server/orderRequests/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRequestService orderRequestService;


    @Test
    void testGetOrders() throws Exception {

        ApplicationUser a = new ApplicationUser("test","p","name1", Role.USER);
        Stock s = new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0);
        Trade t =  new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" );
        OrderRequest o1 =  new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now(), t);
        OrderRequest o2 =  new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now(), t);

        List<OrderRequest> test = new ArrayList<>();

        Mockito.when(orderRequestService.getOrderRequestsByApplicationUser(anyLong())).thenReturn(test);

        RequestBuilder builder = MockMvcRequestBuilders.get(URL + 1L);
        MvcResult r = mockMvc.perform(builder).andReturn();

//        String result = r.getResponse().getContentAsString();
        assertEquals(200, r.getResponse().getStatus());
//        assertEquals("exists", result);
    }

//    @Test
//    void executeOrder() throws Exception {
//
////        Mockito.when(orderRequestService.executeOrder(anyString(),anyString(), anyLong(), anyLong())).thenReturn(test);
//
//        RequestBuilder builder = MockMvcRequestBuilders.post(URL + "execute/type/symbol/" + 1L + "/" + 1L);
//        mockMvc.perform(builder).andReturn();
//
//        Mockito.verify(orderRequestService).executeOrder(anyString(),anyString(), anyLong(), anyLong());
//
//
//
//    }
}