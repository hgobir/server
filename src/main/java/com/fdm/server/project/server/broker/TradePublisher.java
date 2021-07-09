package com.fdm.server.project.server.broker;

import com.fdm.server.project.server.SecurityConfig;
import com.fdm.server.project.server.dto.TradeRequest;
import com.fdm.server.project.server.dto.TradeResponse;
import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.service.OrderRequestService;
import com.fdm.server.project.server.service.TradeService;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/trade")
@Component
public class TradePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;
    private final OrderRequestService orderRequestService;

    @Autowired
    public TradePublisher(RabbitTemplate rabbitTemplate, DirectExchange directExchange, OrderRequestService orderRequestService) {
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.orderRequestService = orderRequestService;
    }

    @PostMapping
    @Async
    @Transactional
    public void placeTrade(@RequestBody TradeRequest tradeRequest) {
        tradeRequest.setTradeId(UUID.randomUUID().toString());
        System.out.println("received trade request in backend!!");


        TradeResponse tradeResponse = rabbitTemplate.convertSendAndReceiveAsType(directExchange.getName(), SecurityConfig.ROUTING_KEY, tradeRequest, new ParameterizedTypeReference<TradeResponse>() {});

//        todo:update status of trade and order

        OrderRequest orderRequest = orderRequestService.getOrderRequestById(tradeRequest.getOrderRequestId()).get();

        orderRequestService.updateTrade(orderRequest.getOrderRequestId(), tradeResponse.getTrade());

        orderRequestService.updateOrderRequestStatus(orderRequest.getOrderRequestId(), OrderRequestStatus.SUCCESSFULLY_COMPLETED);

        System.out.println("this is what trade tradeResponse looks like!! " + tradeResponse);

    }


}
