package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.repository.StockRepository;
import com.fdm.server.project.server.repository.TradeRepository;
import com.fdm.server.project.server.trade.TradePosition;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TradeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TradeRepository underTest;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private StockRepository stockRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        applicationUserRepository.deleteAll();
        stockRepository.deleteAll();
        entityManager.clear();
    }

    @Test
    void testGetTradesByApplicationUser() {

        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.save(a);
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        stockRepository.save(s);
        Trade t =  entityManager.persistAndFlush(new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" ));
        underTest.save(t);
        List<Trade> testTrades = underTest.getTradesByApplicationUser(a);
        assertEquals(1, testTrades.size());
    }

    @Test
    void testGetTradesByStock() {

        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.save(a);
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        stockRepository.save(s);
        Trade t1 =  entityManager.persistAndFlush(new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" ));
        Trade t2 =  entityManager.persistAndFlush(new Trade(TradePosition.SHORT, a, s, 5.0, 5.0, 5L, "test-time2","test-ref2"));
        underTest.save(t1);
        underTest.save(t2);
        List<Trade> testTrades = underTest.getTradesByStock(s);
        assertEquals(2, testTrades.size());
    }
}