package com.fdm.server.project.server.service;

import com.fdm.server.project.server.dto.RegistrationRequest;
import com.fdm.server.project.server.model.StockData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ReportService {

    boolean createReport(Long applicationUserId, String reportType,  String reportFormat, List<StockData> stockDataList) throws IOException, URISyntaxException;





}
