package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.repository.StockRepository;
import com.fdm.server.project.server.util.converter.StockToStockDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
//    private final StockToStockDataConverter stockToStockDataConverter;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository
//                            StockToStockDataConverter stockToStockDataConverter
    ) {
        this.stockRepository = stockRepository;
//        this.stockToStockDataConverter = stockToStockDataConverter;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Stock> getStockById(Long stockId) {
        return stockRepository.findById(stockId);
    }

    public Stock getStockBySymbol(String symbol) {
        return stockRepository.getStockBySymbol(symbol);
    }

    @Override
    @Transactional
    public void saveModulatedStocks(List<Stock> listOfStock) {

        listOfStock.forEach(stock -> {

            boolean stockExists = stockRepository.existsById(stock.getStockId());

            if(stockExists) {

                long updatedVolume = stock.getCurrentVolume();
                double updatedValue = stock.getCurrentValue();

                stockRepository.modulateVolume(stock.getStockId(), updatedVolume);
                stockRepository.modulateValue(stock.getStockId(), updatedValue);

            } else {

                stock.setLastTraded(null);
                stockRepository.save(stock);

            }

        });
    }

    @Override
    @Transactional
    public synchronized int updateLastTraded(Long stockId, String lastTraded) {
        return stockRepository.updateLastTraded(stockId, lastTraded);
    }

    @Override
    @Transactional(readOnly = true)
    public StockData getStock(Long stockId) {
        Stock stock = stockRepository.getStockByStockId(stockId);
        return new StockData(stockId, stock.getSymbol(), stock.getCurrentValue(), stock.getCurrentVolume());
    }

    @Override
    @Transactional(readOnly = true)
    public Stock getStockUsingSymbol(String symbol) {
        return stockRepository.getStockBySymbol(symbol);
    }


}
