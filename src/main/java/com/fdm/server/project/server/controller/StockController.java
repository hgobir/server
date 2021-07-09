package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.service.StockService;
import com.fdm.server.project.server.util.converter.StockToStockDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server/stocks")
public class StockController {

    /*
        todo: remember to sort stock list from descending to ascending!!
     */
    private final StockService stockService;
    private final StockToStockDataConverter stockToStockDataConverter;

    @Autowired
    public StockController(StockService stockService, StockToStockDataConverter stockToStockDataConverter) {
        this.stockService = stockService;
        this.stockToStockDataConverter = stockToStockDataConverter;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Stock> getStocks() {
        return stockService.getStocks();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{stockId}")
    public StockData getStock(@PathVariable("stockId") Long stockId) {
        return stockService.getStock(stockId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/stockData")
    public List<StockData> getStockData() {
        List<Stock> stocks = stockService.getStocks();
        return stockToStockDataConverter.convert(stocks);
    }



}
