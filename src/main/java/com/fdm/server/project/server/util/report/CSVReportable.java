package com.fdm.server.project.server.util.report;

import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.exception.types.ReportGenerationException;
import com.fdm.server.project.server.model.Company;
import com.fdm.server.project.server.model.PortfolioReportModel;
import com.fdm.server.project.server.model.StockReportModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVReportable implements Reportable {

    public static final String KEY = "csv";


    @Override
    public boolean createStockReport(List<StockReportModel> stockReportModels) throws FileNotFoundException {

        File csvFile = new File("C:\\Users\\Hamza\\IdeaProjects\\server\\reports\\csv\\report.csv");

        try {
            PrintWriter out = new PrintWriter(csvFile);
            StringBuffer result = new StringBuffer("\"Symbol\",\"Name\",\"Value\",\"Volume\",\"Gains\"\n");

            for (StockReportModel s : stockReportModels) {

                result.append("\"").append(s.getSymbol()).append("\",");
                result.append("\"").append(s.getName()).append("\",");
                result.append("\"").append(s.getValue()).append("\",");
                result.append("\"").append(s.getVolume()).append("\",");
                result.append("\"").append(s.getGains()).append("\"\n");

            }
            out.println(result);
            out.close();

        } catch (Exception e) {
            throw new ReportGenerationException("error occurred while generating user's stock csv file!");
        }
        return csvFile.exists();
    }

    @Override
    public boolean createPortfolioReport(List<PortfolioReportModel> portfolioReportModels) throws IOException {
        File csvFile = new File("C:\\Users\\Hamza\\IdeaProjects\\server\\reports\\csv\\report.csv");

        try {

            PrintWriter out = new PrintWriter(csvFile);
            StringBuffer result = new StringBuffer("\"Symbol\",\"Name\",\"Invested\",\"Units\",\"Time\",\"Price\"\n");


            for (PortfolioReportModel p : portfolioReportModels) {

                result.append("\"").append(p.getSymbol()).append("\",");
                result.append("\"").append(p.getName()).append("\",");
                result.append("\"").append(p.getInvested()).append("\",");
                result.append("\"").append(p.getUnits()).append("\",");
                result.append("\"").append(p.getTime()).append("\",");
                result.append("\"").append(p.getPrice()).append("\"\n");

            }

//            System.out.println("this is what csv of report looks like!!");

            out.println(result);
            out.close();

//            System.out.println(result.toString());
        } catch (Exception e) {
            throw new ReportGenerationException("error occurred while generating user's portfolio csv file!");

        }

        return csvFile.exists();

    }
}
