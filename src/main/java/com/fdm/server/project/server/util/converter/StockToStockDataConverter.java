package com.fdm.server.project.server.util.converter;

import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
    todo: find better way to implement - must be able to inject implementation at runtime via lambdas but dont have enough time atm!!!!
 */

@Component
public class StockToStockDataConverter implements ConverterService {

    private final StockService stockService;

    @Autowired
    public StockToStockDataConverter(StockService stockService) {
        this.stockService = stockService;
    }


    public List<StockData> convert(List<Stock> stocks) {
        return Collections.unmodifiableList(stocks.stream()
                .map(stock -> new StockData(
                        stock.getStockId(),
                        stock.getSymbol(),
                        stock.getCurrentValue(),
                        stock.getCurrentVolume()))
                .collect(Collectors.toList()));
    }

    public List<Stock> revert(List<StockData> stocksDataList) {
        return Collections.unmodifiableList(stocksDataList.stream()
                .map(stockData -> stockService.getStockUsingSymbol(stockData.getSymbol()))
                .collect(Collectors.toList()));
    }




}
