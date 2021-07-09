package com.fdm.server.project.server.controller;

import com.fdm.server.project.server.entity.Portfolio;
import com.fdm.server.project.server.model.StockData;
import com.fdm.server.project.server.model.StockList;
import com.fdm.server.project.server.service.PortfolioService;
import com.fdm.server.project.server.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/server/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{applicationUserId}/{reportType}/{reportFormat}")
    public ResponseEntity<Resource> getReports(@PathVariable("applicationUserId") Long applicationUserId,
                                               @PathVariable("reportType") String reportType,
                                               @PathVariable("reportFormat") String reportFormat,
                                               @RequestBody List<StockData> stockList,
                                               HttpServletRequest request) throws IOException, URISyntaxException {
//        System.out.println("report api hit!!");
        System.out.println("trade api hit!! - this is reportType ["+ reportType +"], " +
                "this is reportFormat [ "+ reportFormat + "] and this is user [" + applicationUserId + "]");
        stockList.forEach(stockData -> System.out.println("this is stockDataValue " + stockData.toString()));
        boolean isReportCreated = reportService.createReport(applicationUserId, reportType, reportFormat, stockList);

        ResponseEntity<Resource> responseEntity;
        Resource resource;

        if(isReportCreated) {

            File report = new File("C:\\Users\\Hamza\\IdeaProjects\\server\\reports\\"+ reportFormat + "\\report." + reportFormat);

            Path filePath = Paths.get(report.toURI());

            resource = new UrlResource(filePath.toUri());

            String contentType = null;

            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }

            if(contentType == null) {
                contentType = "text/plain";
            }

            System.out.println("this is what content type is! " + contentType);

//            System.out.println("this is total space of report " + resource.getFile().getTotalSpace());

            responseEntity = ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);



            return responseEntity;
        }

        responseEntity = ResponseEntity.badRequest().build();
        return responseEntity;
    }



}
