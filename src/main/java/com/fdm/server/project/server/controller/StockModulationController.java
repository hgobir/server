package com.fdm.server.project.server.controller;


import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.Company;
import com.fdm.server.project.server.model.CompanyList;
import com.fdm.server.project.server.model.ListOfIds;
import com.fdm.server.project.server.model.StockList;
import com.fdm.server.project.server.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1/server/stockModulation")
public class StockModulationController {

//  todo - ensure this controller runs on separate thread to main application thread
//    todo - could also define api to start thread at 8am and stop at 16:30pm??

    private final StockService stockService;
    private static final String STOCK_MODULATION_URL = "http://localhost:8080/api/v1/stockModulation/";
    private final RestTemplate restTemplate;


    @Autowired
    public StockModulationController(StockService stockService, RestTemplate restTemplate) {
        this.stockService = stockService;
        this.restTemplate = restTemplate;
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/currentStockPrices")
    public void receiveCurrentStockData(@RequestBody StockList stockList) {
        List<Stock> listOfStock = stockList.getStockList();
        listOfStock.forEach(stock -> System.out.println(stock.getStockId()));
        stockService.saveModulatedStocks(listOfStock);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/getCompany")
    public Company getCompany(Long stockId) {
//        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(STOCK_MODULATION_URL + "admin/companies/" + stockId, Company.class);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value ="/getCompanies")
    public List<Company> getCompanies(List<Long> stockIds) {


//        stockIds.forEach(stockId -> System.out.println("this is one of stock ids about to send ["+stockId+"]"));
        ListOfIds stockIdList = new ListOfIds(stockIds);

        CompanyList companyList = restTemplate.postForObject(STOCK_MODULATION_URL + "admin/companies/specific", stockIdList, CompanyList.class);


//        System.out.println("this is companylist object " + companyList.toString());

        return companyList.getCompanyList();
//        return restTemplate.getForObject(STOCK_MODULATION_URL + "admin/companies/specific", CompanyList.class);
        }
}
