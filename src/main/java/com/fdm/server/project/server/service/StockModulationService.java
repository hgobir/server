package com.fdm.server.project.server.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface StockModulationService {

    String getCompanyName(Long stockId);

    Map<Long,String> getCompanyNames(List<Long> stockIds) throws MalformedURLException, URISyntaxException;
}
