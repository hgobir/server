package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.repository.PortfolioRepository;
import com.fdm.server.project.server.repository.StockRepository;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase
class PortfolioRepositoryIntegrationTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PortfolioRepository underTest;

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
    void testFindPortfolioByApplicationUserAndStock() {

        ApplicationUser a1 = entityManager.persistAndFlush(new ApplicationUser("test1","p1","name1", Role.USER));
        ApplicationUser a2 = entityManager.persistAndFlush(new ApplicationUser("test2","p2","name2", Role.USER));
        applicationUserRepository.save(a1);
        applicationUserRepository.save(a2);
        Stock s1 = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0));
        Stock s2 = entityManager.persistAndFlush(new Stock(2L,2.0,2L,"TEST2",10.0,10.0,20.0));
        stockRepository.save(s1);
        stockRepository.save(s2);
        Portfolio p1 =  entityManager.persistAndFlush(new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0));
        Portfolio p2 =  entityManager.persistAndFlush(new Portfolio(s2, 2L, 20.0 , a2, LocalDateTime.now(), 20.0));
        underTest.saveAndFlush(p1);
        underTest.saveAndFlush(p2);

        Portfolio testPortfolio = underTest.findPortfolioByApplicationUserAndStock(a1, s1);

        assertThat(testPortfolio).isNotNull();
    }

    @Test
    void testFindPortfolioByApplicationUser() {

        ApplicationUser a1 = entityManager.persistAndFlush(new ApplicationUser("test1","p1","name1", Role.USER));
        ApplicationUser a2 = entityManager.persistAndFlush(new ApplicationUser("test2","p2","name2", Role.USER));
        applicationUserRepository.save(a1);
        applicationUserRepository.save(a2);
        Stock s1 = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0));
        Stock s2 = entityManager.persistAndFlush(new Stock(2L,2.0,2L,"TEST2",10.0,10.0,20.0));
        stockRepository.save(s1);
        stockRepository.save(s2);
        Portfolio p1 =  entityManager.persistAndFlush(new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0));
        Portfolio p2 =  entityManager.persistAndFlush(new Portfolio(s2, 2L, 20.0 , a2, LocalDateTime.now(), 20.0));
        underTest.saveAndFlush(p1);
        underTest.saveAndFlush(p2);

        List<Portfolio> testPortfolios = underTest.findPortfolioByApplicationUser(a1);

        assertEquals(1, testPortfolios.size());
    }

    @Test
    void testFindPortfolioByPortfolioId() {

        entityManager.clear();

        ApplicationUser a1 = entityManager.persistAndFlush(new ApplicationUser("test1","p1","name1", Role.USER));
        ApplicationUser a2 = entityManager.persistAndFlush(new ApplicationUser("test2","p2","name2", Role.USER));
        applicationUserRepository.save(a1);
        applicationUserRepository.save(a2);
        entityManager.refresh(a1);
        entityManager.refresh(a2);
        entityManager.detach(a1);
        entityManager.detach(a2);

        entityManager.flush();

        Stock s1 = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST1",5.0,5.0,10.0));
        Stock s2 = entityManager.persistAndFlush(new Stock(2L,2.0,2L,"TEST2",10.0,10.0,20.0));
        stockRepository.save(s1);
        stockRepository.save(s2);

        Portfolio p1 =  entityManager.persistAndFlush(new Portfolio(s1, 1L, 10.0 , a1, LocalDateTime.now(), 10.0));
        underTest.saveAndFlush(p1);
        entityManager.refresh(p1);

        Portfolio testPortfolio = underTest.findPortfolioByPortfolioId(5L);

        assertThat(testPortfolio).isNotNull();

    }
}