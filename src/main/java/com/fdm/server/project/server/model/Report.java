package com.fdm.server.project.server.model;

import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.util.report.Reportable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Report {

    /*
        todo - using composition/ stragegy pattern!!
     */
    private Reportable reportType;
    private List<Portfolio> portfolioList;
    private List<StockData> stockDataList;
    private Map<Long,String> companyListStructure;
    private Company company;
    private Stock stock;
    private List<StockReportModel> stockReportModels;
    private List<PortfolioReportModel> portfolioReportModels;

    public Report() {
    }

    public Report(Reportable reportType) {
        this.reportType = reportType;
    }


    public Reportable getReportType() {
        return reportType;
    }

    public void setReportType(Reportable reportType) {
        this.reportType = reportType;
    }

    public List<StockReportModel> getStockReportModels() {
        return stockReportModels;
    }

    public void setStockReportModels(List<StockReportModel> stockReportModels) {
        this.stockReportModels = stockReportModels;
    }
    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(List<Portfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<StockData> getStockDataList() {
        return stockDataList;
    }

    public void setStockDataList(List<StockData> stockDataList) {
        this.stockDataList = stockDataList;
    }

    public List<PortfolioReportModel> getPortfolioReportModels() {
        return portfolioReportModels;
    }

    public void setPortfolioReportModels(List<PortfolioReportModel> portfolioReportModels) {
        this.portfolioReportModels = portfolioReportModels;
    }

    public Map<Long, String> getCompanyListStructure() {
        return companyListStructure;
    }

    public void setCompanyListStructure(Map<Long, String> companyListStructure) {
        this.companyListStructure = companyListStructure;
    }

    public boolean generateReport() throws IOException {
//        System.out.println("this is size of stockReportModel [" + this.stockReportModels.size() + "]");
//        System.out.println("this is size of portfolioReportModel [" + this.portfolioReportModels.size() + "]");

        if(this.stockReportModels != null) {

            return reportType.createStockReport(stockReportModels);

        }
        return reportType.createPortfolioReport(portfolioReportModels);

    }

    public boolean regenerateReportWithAnotherFormat(Reportable newReportable) throws IOException {
        this.reportType = newReportable;
        return generateReport();
    }
}
