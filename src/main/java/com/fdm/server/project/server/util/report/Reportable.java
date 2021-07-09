package com.fdm.server.project.server.util.report;




import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.entity.Stock;
import com.fdm.server.project.server.model.Company;
import com.fdm.server.project.server.model.PortfolioReportModel;
import com.fdm.server.project.server.model.StockReportModel;

import java.io.IOException;
import java.util.List;

public interface Reportable {

    boolean createStockReport(List<StockReportModel> stockReportModels) throws IOException;
    boolean createPortfolioReport(List<PortfolioReportModel> portfolioReportModels) throws IOException;
}
