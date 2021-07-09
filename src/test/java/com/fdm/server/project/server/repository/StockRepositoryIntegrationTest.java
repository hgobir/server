package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class StockRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        entityManager.clear();
    }

    @Test
    void testModulateVolume() {
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        underTest.save(s);
        entityManager.refresh(s);
        underTest.modulateVolume(1L, 5L);
        entityManager.refresh(s);
        Stock testStock = underTest.getStockByStockId(1L);
        assertEquals(5L, testStock.getCurrentVolume());
        entityManager.detach(s);
    }

    @Test
    void testModulateValue() {
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        underTest.save(s);
        entityManager.refresh(s);
        underTest.modulateValue(1L, 5.0);
        entityManager.refresh(s);
        Stock testStock = underTest.getStockByStockId(1L);
        assertEquals(5.0, testStock.getCurrentValue());
        entityManager.detach(s);
    }

    @Test
    void testUpdateLastTraded() {
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        underTest.save(s);
        entityManager.refresh(s);
        underTest.updateLastTraded(1L, "2021-06-21T22:48:08.373");
        entityManager.refresh(s);
        Stock testStock = underTest.getStockByStockId(1L);
        assertEquals("2021-06-21T22:48:08.373", testStock.getLastTraded());
        entityManager.detach(s);
    }

    @Test
    void testGetStockBySymbol() {
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        underTest.save(s);
        entityManager.refresh(s);
        Stock testStock = underTest.getStockBySymbol("TEST");
        entityManager.refresh(s);
        assertThat(testStock).isNotNull();
        entityManager.detach(s);
    }

    @Test
    void testGetStockByStockId() {
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        underTest.save(s);
        entityManager.refresh(s);
        Stock testStock = underTest.getStockByStockId(1L);
        entityManager.refresh(s);
        assertThat(testStock).isNotNull();
        entityManager.detach(s);
    }
}