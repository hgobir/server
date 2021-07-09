package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.StockData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StockService {

    List<Stock> getStocks();

    Optional<Stock> getStockById(Long stockId);

    Stock getStockUsingSymbol(String symbol);

    void saveModulatedStocks(List<Stock> listOfStock);

    int updateLastTraded(Long stockId, String lastTraded);

    StockData getStock(Long stockId);

//    List<StockData> getSpecificStocks(Long[] stockIds);
//
//    List<StockData> getStockData();
}
