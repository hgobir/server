package com.fdm.server.project.server.controller;


import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.service.OrderRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server/orderRequests")
public class OrderRequestController {

        /*
        todo: remember to sort order list from descending to ascending!!
     */

    private final OrderRequestService orderRequestService;

    @Autowired
    public OrderRequestController(OrderRequestService orderRequestService) {
        this.orderRequestService = orderRequestService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{applicationUserId}")
    public List<OrderRequest> getOrders(@PathVariable("applicationUserId") Long applicationUserId) {
        System.out.println("orders api hit!!");

        return orderRequestService.getOrderRequestsByApplicationUser(applicationUserId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/execute/{type}/{symbol}/{numberOfShares}/{applicationUserId}")
    @Async
    public void executeOrder(@PathVariable("type") String type,
                             @PathVariable("symbol") String symbol,
                             @PathVariable("numberOfShares") Long numberOfShares,
                             @PathVariable("applicationUserId") Long applicationUserId) {
        orderRequestService.executeOrder(type, symbol, numberOfShares, applicationUserId);
    }


}
