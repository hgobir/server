package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Modifying
    @Query("UPDATE Stock s " +
            "SET s.currentVolume = ?2 WHERE s.stockId = ?1")
    void modulateVolume(Long stockId, Long volume);

    @Modifying
    @Query("UPDATE Stock s " +
            "SET s.currentValue = ?2 WHERE s.stockId = ?1")
    void modulateValue(Long stockId, Double value);

    @Modifying
    @Query("UPDATE Stock s " +
            "SET s.lastTraded = ?2 WHERE s.stockId = ?1")
    int updateLastTraded(Long stockId, String lastTraded);

    @Query("SELECT s FROM Stock s WHERE s.symbol = ?1")
    Stock getStockBySymbol(String symbol);

    @Query("SELECT s FROM Stock s WHERE s.stockId = ?1")
    Stock getStockByStockId(Long stockId);




}
