package com.fdm.server.project.server.repository;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("SELECT p FROM Portfolio p WHERE p.applicationUser = ?1 and p.stock = ?2")
    Portfolio findPortfolioByApplicationUserAndStock(ApplicationUser applicationUser, Stock stock);

    @Query("SELECT p FROM Portfolio p WHERE p.applicationUser = ?1")
    List<Portfolio> findPortfolioByApplicationUser(ApplicationUser applicationUser);

    @Query("SELECT p FROM Portfolio p WHERE p.portfolioId = ?1")
    Portfolio findPortfolioByPortfolioId(Long portfolioId);
}
