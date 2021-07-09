package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import com.fdm.server.project.server.repository.ApplicationUserRepository;
import com.fdm.server.project.server.repository.OrderRequestRepository;
import com.fdm.server.project.server.repository.StockRepository;
import com.fdm.server.project.server.repository.TradeRepository;
import com.fdm.server.project.server.trade.OrderRequestStatus;
import com.fdm.server.project.server.trade.TradePosition;
import com.fdm.server.project.server.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase
class OrderRequestRepositoryIntegrationTest {

    @Autowired
    private OrderRequestRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private StockRepository stockRepository;


    @AfterEach
    void tearDown() {
        underTest.deleteAll();
        applicationUserRepository.deleteAll();
        stockRepository.deleteAll();
        tradeRepository.deleteAll();
        entityManager.clear();
    }



    @Test
    void testFindOrderRequestsByApplicationUser() {
        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.save(a);
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        stockRepository.save(s);
//        Trade t =  entityManager.persistAndFlush(new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" ));
//        tradeRepository.save(t);
        OrderRequest o =  entityManager.persistAndFlush(new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now()));
        underTest.save(o);
        List<OrderRequest> testOrderRequests = underTest.findOrderRequestsByApplicationUser(a);
        assertEquals(1, testOrderRequests.size());
        entityManager.detach(o);

    }

    @Test
    void testFindOrderRequestByApplicationUserAndStockAndNumberOfShares() {

        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.save(a);
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        stockRepository.save(s);
        OrderRequest o =  entityManager.persistAndFlush(new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now()));
        underTest.save(o);
        OrderRequest testOrderRequest = underTest.findOrderRequestByApplicationUserAndStockAndNumberOfShares(a, s, 1L);

        assertThat(testOrderRequest).isNotNull();
        entityManager.detach(o);
    }

    @Test
    void testUpdateTrade() {

        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.save(a);
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        stockRepository.save(s);
        Trade t =  entityManager.persistAndFlush(new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" ));
        tradeRepository.saveAndFlush(t);
        OrderRequest o =  entityManager.persistAndFlush(new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now(), null));
        underTest.save(o);

        underTest.updateTrade(1L, t);
//
        entityManager.refresh(o);

        OrderRequest testOrderRequest = underTest.findById(1L).get();

        assertThat(testOrderRequest.getTrade().toString()).isEqualTo(t.toString());

        entityManager.detach(o);


    }

    @Test
    void testUpdateOrderRequestStatus() {

        ApplicationUser a = entityManager.persistAndFlush(new ApplicationUser("test","p","name1", Role.USER));
        applicationUserRepository.save(a);
        entityManager.detach(a);
        Stock s = entityManager.persistAndFlush(new Stock(1L,1.0,1L,"TEST",5.0,5.0,10.0));
        stockRepository.save(s);
        entityManager.detach(s);
        Trade t =  entityManager.persistAndFlush(new Trade(TradePosition.LONG,  a, s, 1.0, 1.0, 1L, "test-time","test-ref" ));
        tradeRepository.saveAndFlush(t);
        entityManager.detach(t);
        OrderRequest o =  entityManager.persistAndFlush(new OrderRequest(OrderRequestStatus.INITIATED.name(), s, 1L, a, LocalDateTime.now(), null));
        underTest.save(o);
        entityManager.refresh(o);
//        List<OrderRequest> testOR = underTest.findAll();

        underTest.updateOrderRequestStatus(4L, OrderRequestStatus.USER_DOESNT_HAVE_ANY_MONEY.name());
        entityManager.refresh(o);

        OrderRequest testOrderRequest = underTest.findById(4L).get();

        assertThat(testOrderRequest.getOrderStatus()).isEqualTo(OrderRequestStatus.USER_DOESNT_HAVE_ANY_MONEY.name());

        entityManager.detach(o);
    }
}