package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final ApplicationUserService applicationUserService;
    private final StockService stockService;

    @Autowired
    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, ApplicationUserService applicationUserService, StockService stockService) {
        this.portfolioRepository = portfolioRepository;
        this.applicationUserService = applicationUserService;
        this.stockService = stockService;
    }

    @Override
    @Transactional(readOnly = true)
    public Portfolio getPortfolioDataAboutStockForUser(Long applicationUserId, Long stockId) {
        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        Stock stock = stockService.getStockById(stockId).get();
        Portfolio stockPortfolio = portfolioRepository.findPortfolioByApplicationUserAndStock(applicationUser, stock);
//        System.out.println("thsi is portfolio returned from repository " + stockPortfolio.toString());
        return stockPortfolio;
    }

    @Override
    @Transactional
    public Portfolio savePortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Portfolio> getPortfolioDataForUser(Long applicationUserId) {
        ApplicationUser applicationUser = applicationUserService.getOneUser(applicationUserId);
        return portfolioRepository.findPortfolioByApplicationUser(applicationUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Portfolio getSpecificPortfolio(Long portfolioId) {
        return portfolioRepository.findPortfolioByPortfolioId(portfolioId);
    }

}
