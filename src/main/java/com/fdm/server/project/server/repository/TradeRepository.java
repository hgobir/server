package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT t FROM Trade t WHERE t.applicationUser = ?1")
    List<Trade> getTradesByApplicationUser(ApplicationUser applicationUser);

    @Query("SELECT t FROM Trade t WHERE t.stock = ?1")
    List<Trade> getTradesByStock(Stock stock);
}
