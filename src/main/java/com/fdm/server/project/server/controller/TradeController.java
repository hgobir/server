package com.fdm.server.project.server.controller;


import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server/trade")
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{applicationUserId}/{stockId}")
    public List<Trade> getTradesPerStock(@PathVariable("applicationUserId") Long applicationUserId,
                                        @PathVariable("stockId") Long stockId) {
        System.out.println("trade api hit!! - this is stock ["+ stockId +"] and this is user [" + applicationUserId + "]");
        return tradeService.getTradesPerStockForUser(applicationUserId, stockId);
    }
}
