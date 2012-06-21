package org.wv.stepsovc.web.controllers;

import fixtures.StepsovcCaseFixture;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.repository.AllReferrals;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.services.BeneficiaryService;
import org.wv.stepsovc.core.services.ReferralService;
import org.wv.stepsovc.core.utils.DateUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;
import static org.motechproject.util.DateUtil.today;
import static org.wv.stepsovc.web.controllers.ReferralExportController.CONTENT_DISPOSITION;
import static org.wv.stepsovc.web.controllers.ReferralExportController.TEXT_CSV;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:testWebApplicationContext.xml")
public class ReferralExportControllerIT {

    @Autowired
    private ReferralService referralService;
    @Autowired
    private AllReferrals allReferrals;
    @Autowired
    private BeneficiaryService beneficiaryService;
    @Autowired
    private ReferralExportController referralExportController;
    private MockHttpServletResponse mockHttpResponse;

    @Test
    public void shouldExportReferralsAsCSV() throws InvocationTargetException, IllegalAccessException, UnsupportedEncodingException {
        mockHttpResponse = new MockHttpServletResponse();
        String benCode = "testBEN1";
        String fileName = "fileName";

        StepsovcCase beneficiaryCase = StepsovcCaseFixture.createNewBeneficiaryCase(benCode);
        beneficiaryService.createBeneficiary(beneficiaryCase);

        String twoDaysBefore = DateUtils.getFormattedDate(today().minusDays(2).toDate());

        StepsovcCase referralCase = StepsovcCaseFixture.createCaseForReferral(benCode, twoDaysBefore, "testFAC1");
        referralService.addNewReferral(referralCase);

        Method exportMethod = getMethodForRequestMapping("/{groupName}/{reportName}.csv", RequestMethod.GET);

        exportMethod.invoke(referralExportController, "referrals", fileName, mockHttpResponse);

        assertEquals("inline; filename=" + fileName + ".csv", mockHttpResponse.getHeaderValue(CONTENT_DISPOSITION));
        assertEquals(TEXT_CSV, mockHttpResponse.getContentType());
        assertEquals(expectedCSVContent(benCode), mockHttpResponse.getContentAsString());
    }

    private String expectedCSVContent(String benCode) {
        Referral activeReferral = allReferrals.findActiveReferral(benCode);
        return "Referral Id,Beneficiary Id,Caregiver Id,Followup Required,Service Details,Service Date,Facility Code,Signed,Signed Date,Art Received,Art Referred,Condoms Received,Condoms Referred,Counselling Testing Received,Counselling Testing Referred,Diagnosis Received,Diagnosis Referred,Family Planning Received,Family Planning Referred,Hospital Admission Received,Hospital Admission Referred,Other Service Referred,Other Service Received,Pain Management Received,Pain Management Referred,Pmtct Received,Pmtct Referred,Sex Trans Infection Referred,Sex Trans Infection Received\n" +
                activeReferral.getOvcId() + ",BenId,Userid001,true,new service details,2012-06-19,testFAC1,false,,false,true,false,true,false,true,false,true,false,true,false,false,true,false,false,true,false,true,false,false\n";
    }

    @After
    public void tearDown() {
        allReferrals.removeAll();
    }

    private Method getMethodForRequestMapping(String requestMapping, RequestMethod requestMethod) {
        for (Method method : ReferralExportController.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                if (annotation.value()[0].equals(requestMapping) && annotation.method()[0].equals(requestMethod)) {
                    return method;
                }
            }
        }
        throw new RuntimeException("Method for mapping not found");
    }
}
