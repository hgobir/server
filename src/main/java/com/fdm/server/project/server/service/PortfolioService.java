package com.fdm.server.project.server.service;

import com.fdm.server.project.server.entity.ApplicationUser;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;

import java.util.List;

public interface PortfolioService {

    Portfolio getPortfolioDataAboutStockForUser(Long applicationUserId, Long stockId);

    Portfolio savePortfolio(Portfolio portfolio);

    List<Portfolio> getPortfolioDataForUser(Long applicationUserId);

    Portfolio getSpecificPortfolio(Long portfolioId);
}
