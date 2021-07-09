package com.fdm.server.project.server.service;


import com.fdm.server.project.server.controller.StockModulationController;
import com.fdm.server.project.server.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockModulationServiceImpl implements StockModulationService {

    private final StockModulationController stockModulationController;

    @Autowired
    public StockModulationServiceImpl(StockModulationController stockModulationController) {
        this.stockModulationController = stockModulationController;
    }

    @Override
    @Transactional
    public String getCompanyName(Long stockId) {
        Company company = stockModulationController.getCompany(stockId);
        return company.getName();
    }

    @Override
    @Transactional
    public Map<Long,String> getCompanyNames(List<Long> stockIds) {

        List<Company> companies = stockModulationController.getCompanies(stockIds);

        return companies.stream().collect(Collectors.toMap(Company::getCompanyId, Company::getName));
    }
}
