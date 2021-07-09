package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.service.PortfolioService;
import com.fdm.server.project.server.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server/portfolio")
public class PortfolioController {

    /*
        todo: remember to sort portfolio list from descending to ascending!!
     */
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{applicationUserId}/{stockId}")
    public Portfolio getPortfolioForStock(@PathVariable("applicationUserId") Long applicationUserId,
                                          @PathVariable("stockId") Long stockId) {
//        System.out.println("portfolio api hit!!");
        return portfolioService.getPortfolioDataAboutStockForUser(applicationUserId, stockId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/specific/{portfolioId}")
    public Portfolio getSpecificPortfolioForStock(@PathVariable("portfolioId") Long portfolioId) {
        System.out.println("specific portfolio api hit!!");
        return portfolioService.getSpecificPortfolio(portfolioId);
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{applicationUserId}")
    public List<Portfolio> getPortfolios(@PathVariable("applicationUserId") Long applicationUserId) {
        return portfolioService.getPortfolioDataForUser(applicationUserId);
    }




}
