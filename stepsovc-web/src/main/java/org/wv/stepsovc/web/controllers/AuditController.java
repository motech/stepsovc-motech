package org.wv.stepsovc.web.controllers;

import org.joda.time.DateTime;
import org.motechproject.sms.api.SMSRecord;
import org.motechproject.sms.api.service.SmsAuditService;
import org.motechproject.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;
import static java.util.Arrays.asList;

@Controller
public class AuditController {
    @Autowired
    private SmsAuditService smsAuditService;

    @RequestMapping("/audits/sms/outbound")
    public void showAllOutboundSMSAudits(HttpServletResponse response) throws IOException {
        List<SMSRecord> allOutboundMessages = smsAuditService.allOutboundMessagesBetween(DateTime.now().minusDays(2), DateTime.now());
        List<SMSRecord> messageAudits = sort(allOutboundMessages, on(SMSRecord.class).getMessageTime(), sortComparator());

        StringBuilder builder = new StringBuilder();
        builder.append("<div id='server_time'>" + DateUtil.now() + "</div>");
        prepareHTMLTable(messageAudits, builder);
        response.getWriter().write(builder.toString());
    }

    @RequestMapping("/audits/sms/inbound")
    public void showAllInboundSMSAudits(HttpServletResponse response) throws IOException {
        List<SMSRecord> allInboundMessages = smsAuditService.allInboundMessagesBetween(DateTime.now().minusDays(2), DateTime.now());
        List<SMSRecord> messageAudits = sort(allInboundMessages, on(SMSRecord.class).getMessageTime(), sortComparator());

        StringBuilder builder = new StringBuilder();
        builder.append("<div id='server_time'>" + DateUtil.now() + "</div>");
        prepareHTMLTable(messageAudits, builder);
        response.getWriter().write(builder.toString());
    }

    private void prepareHTMLTable(List<SMSRecord> messageAudits, StringBuilder builder) {
        builder.append("<table border='1' width='100%'>");
        row(builder, header("Subscriber", "Sent on", "Content"));
        for (SMSRecord smsRecord : messageAudits) {
            List<? extends Object> dataList = asList(smsRecord.getPhoneNo(), smsRecord.getMessageTime().toString(),
                    smsRecord.getContent());
            rowData(builder, dataList);
        }

        builder.append("</table>");
    }

    private Comparator<DateTime> sortComparator() {
        return new Comparator<DateTime>() {
            @Override
            public int compare(DateTime o1, DateTime o2) {
                return o2.compareTo(o1);
            }
        };
    }

    private AuditController row(StringBuilder buffer, String data) {
        buffer.append("<tr>").append(data).append("</tr>");
        return this;
    }

    private AuditController rowData(StringBuilder buffer, List<? extends Object> dataList) {
        buffer.append("<tr>");
        for (Object data : dataList) buffer.append("<td>").append(data).append("</td>");
        buffer.append("</tr>");
        return this;
    }

    private String header(Object... headers) {
        StringBuilder builder = new StringBuilder();
        for (Object header : headers) {
            builder.append("<th>").append(header).append("</th>");
        }
        return builder.toString();
    }
}
