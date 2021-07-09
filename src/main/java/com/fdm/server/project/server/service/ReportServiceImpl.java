package com.fdm.server.project.server.service;

import com.fdm.server.project.server.exception.types.InvalidReportFormatException;
import com.fdm.server.project.server.model.PortfolioReportModel;
import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.Report;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.model.StockReportModel;
import com.fdm.server.project.server.util.converter.StockToStockDataConverter;
import com.fdm.server.project.server.util.report.CSVReportable;
import com.fdm.server.project.server.util.report.XMLReportable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final StockToStockDataConverter stockToStockDataConverter;
    private final PortfolioService portfolioService;
    private final StockModulationService stockModulationService;

    @Autowired
    public ReportServiceImpl(StockToStockDataConverter stockToStockDataConverter,
                             PortfolioService portfolioService,
                             StockModulationService stockModulationService) {
        this.stockToStockDataConverter = stockToStockDataConverter;
        this.portfolioService = portfolioService;
        this.stockModulationService = stockModulationService;
    }

    @Override
    @Transactional
    public boolean createReport(Long applicationUserId, String reportType, String reportFormat, List<StockData> stockDataList) throws IOException, URISyntaxException {


        if(!reportFormat.equalsIgnoreCase("csv") && !reportFormat.equalsIgnoreCase("xml")) {
            throw new InvalidReportFormatException("Server does not support filetype ["+ reportFormat + "] - Please use valid format", applicationUserId);
        }

        Report report = reportFormat.equalsIgnoreCase("csv")
                ? new Report(new CSVReportable()) : new Report(new XMLReportable());

        List<Long> stockIds;

        if(reportType.equalsIgnoreCase("stock")) {
            report.setStockDataList(stockDataList);
            stockIds = stockDataList.stream().map(StockData::getStockDataId).collect(Collectors.toList());
            Map<Long,String> companyNames = stockModulationService.getCompanyNames(stockIds);
//            System.out.println("below is list of company names!");
            List<StockReportModel> stockReportModels = createStockReportFramework(companyNames, stockDataList);
            report.setStockReportModels(stockReportModels);
        } else {
            List<Portfolio> portfolioList = portfolioService.getPortfolioDataForUser(applicationUserId);
            report.setPortfolioList(portfolioList);
            stockIds = portfolioList.stream().map(p -> p.getStock().getStockId()).collect(Collectors.toList());
            Map<Long,String> companyNames = stockModulationService.getCompanyNames(stockIds);
            List<PortfolioReportModel> portfolioReportModels = createPortfolioReportFramework(companyNames, portfolioList);
            report.setPortfolioReportModels(portfolioReportModels);
        }
        return report.generateReport();
    }


    private List<StockReportModel> createStockReportFramework(Map<Long, String> companyNames, List<StockData> stockDataList) {

        List<Stock> stockList = stockToStockDataConverter.revert(stockDataList);

        List<StockReportModel> newStockReport = stockList.stream().map(stock ->
            new StockReportModel(stock.getSymbol(),
                    companyNames.get(stock.getStockId()).replaceAll("&", "&amp;"),
                    stock.getCurrentValue(),
                    stock.getCurrentVolume(),
                    stock.getGains())
        ).collect(Collectors.toList());

        return newStockReport.stream()
                .sorted(Comparator.comparing(StockReportModel::getName))
                .collect(Collectors.toList());
    }

    private List<PortfolioReportModel> createPortfolioReportFramework(Map<Long, String> companyNames, List<Portfolio> portfolioList) {

        List<PortfolioReportModel> newPortfolioReport = portfolioList.stream().map(portfolio ->
                new PortfolioReportModel(portfolio.getStock().getSymbol(),
                        companyNames.get(portfolio.getStock().getStockId()).replaceAll("&", "&amp;"),
                        portfolio.getTotalInvested(),
                        portfolio.getUnit(),
                        portfolio.getLastTimeTraded().toLocalDate().toString(),
                        portfolio.getLastPerShareTradedPrice())
        ).collect(Collectors.toList());

        return newPortfolioReport.stream()
                .sorted(Comparator.comparing(PortfolioReportModel::getName))
                .collect(Collectors.toList());
    }


}
