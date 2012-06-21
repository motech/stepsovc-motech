package org.wv.stepsovc.web.controllers;

import org.motechproject.export.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/export")
@Controller
public class ReferralExportController {

    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String TEXT_CSV = "text/csv";
    @Autowired
    private ExportService exportService;

    @RequestMapping(method = RequestMethod.GET, value = "/{groupName}/{reportName}.csv")
    public void exportReferralsAsCSV(@PathVariable("groupName") String groupName, @PathVariable("reportName") String reportName,
                                     HttpServletResponse response) throws IOException {
        initializeCSVResponse(response, reportName + ".csv");
        exportService.exportAsCSV(groupName, response.getWriter());
    }

    private void initializeCSVResponse(HttpServletResponse response, String fileName) {
        response.setHeader(CONTENT_DISPOSITION, "inline; filename=" + fileName);
        response.setContentType(TEXT_CSV);
    }
}
