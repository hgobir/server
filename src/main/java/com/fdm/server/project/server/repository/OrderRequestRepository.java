package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.OrderRequest;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {

    @Query("SELECT o FROM OrderRequest o WHERE o.applicationUser = ?1")
    List<OrderRequest> findOrderRequestsByApplicationUser(ApplicationUser applicationUser);

    @Query("SELECT o FROM OrderRequest o WHERE o.applicationUser = ?1 and o.stock = ?2 and o.numberOfShares = ?3")
    OrderRequest findOrderRequestByApplicationUserAndStockAndNumberOfShares(ApplicationUser applicationUser, Stock stock, Long numberOfShares);

    @Transactional
    @Modifying
    @Query("UPDATE OrderRequest o " +
            "SET o.trade = ?2 " +
            "WHERE o.orderRequestId = ?1")
    int updateTrade(Long orderRequestId, Trade trade);

    @Transactional
    @Modifying
    @Query("UPDATE OrderRequest o " +
            "SET o.orderRequestStatus = ?2 " +
            "WHERE o.orderRequestId = ?1")
    int updateOrderRequestStatus(Long orderRequestId, String orderRequestStatus);
}
