package org.wv.stepsovc.web.controllers;

import org.motechproject.export.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/export")
@Controller
public class ReferralExportController {

    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String TEXT_CSV = "text/csv";
    @Autowired
    private ExportService exportService;
    @Value("#{stepovcProperties['export.key']}")
    private String key;

    @RequestMapping(method = RequestMethod.GET, value = "/{groupName}/{reportName}.csv")
    public void exportReferralsAsCSV(@PathVariable("groupName") String groupName, @PathVariable("reportName") String reportName,
                                     @RequestParam("key") String requestKey, HttpServletResponse response) throws IOException {
        initializeCSVResponse(response, reportName + ".csv");

        if (key.equals(requestKey))
            exportService.exportAsCSV(groupName, response.getWriter());
        else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void initializeCSVResponse(HttpServletResponse response, String fileName) {
        response.setHeader(CONTENT_DISPOSITION, "inline; filename=" + fileName);
        response.setContentType(TEXT_CSV);
    }
}
