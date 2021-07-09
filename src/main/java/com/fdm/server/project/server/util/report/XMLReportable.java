package com.fdm.server.project.server.util.report;

//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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

public class XMLReportable implements Reportable {

    public static final String KEY = "xml";

    @Override
    public boolean createStockReport(List<StockReportModel> stockReportModels) {

        File xmlFile = new File("C:\\Users\\Hamza\\IdeaProjects\\server\\reports\\xml\\report.xml");

        try {
            PrintWriter out = new PrintWriter(xmlFile);

            StringBuffer result = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            result.append("<StockReport>\n");

            for (StockReportModel s : stockReportModels) {

                result.append("\t<Stock>\n");
                result.append("\t\t<Symbol>").append(s.getSymbol()).append("</Symbol>\n");
                result.append("\t\t<Name>").append(s.getName()).append("</Name>\n");
                result.append("\t\t<Value>").append(s.getValue()).append("</Value>\n");
                result.append("\t\t<Volume>").append(s.getVolume()).append("</Volume>\n");
                result.append("\t\t<Gains>").append(s.getGains()).append("</Gains>\n");
                result.append("\t</Stock>\n");

            }
            result.append("</StockReport>\n");

            out.println(result);
            out.close();

        } catch (Exception e) {
            throw new ReportGenerationException("error occurred while generating user's stock xml file!");

        }

        return xmlFile.exists();

    }

    @Override
    public boolean createPortfolioReport(List<PortfolioReportModel> portfolioReportModels) throws IOException {
        File xmlFile = new File("C:\\Users\\Hamza\\IdeaProjects\\server\\reports\\xml\\report.xml");

        try {
            PrintWriter out = new PrintWriter(xmlFile);

            StringBuffer result = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            result.append("<PortfolioReport>\n");

            for (PortfolioReportModel p : portfolioReportModels) {

                result.append("\t<Portfolio>\n");
                result.append("\t\t<Symbol>").append(p.getSymbol()).append("</Symbol>\n");
                result.append("\t\t<Name>").append(p.getName()).append("</Name>\n");
                result.append("\t\t<Invested>").append(p.getInvested()).append("</Invested>\n");
                result.append("\t\t<Units>").append(p.getUnits()).append("</Units>\n");
                result.append("\t\t<Time>").append(p.getTime()).append("</Time>\n");
                result.append("\t\t<Price>").append(p.getPrice()).append("</Price>\n");
                result.append("\t</Portfolio>\n");

            }
            result.append("</PortfolioReport>\n");

            out.println(result);
            out.close();

        } catch (Exception e) {
            throw new ReportGenerationException("error occurred while generating user's portfolio xml file!");
        }
        return xmlFile.exists();

    }

}
