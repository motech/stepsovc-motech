package org.wv.stepsovc.commcare.gateway;

import org.apache.velocity.app.VelocityEngine;
import org.motechproject.http.client.listener.HttpClientEventListener;
import org.motechproject.http.client.service.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.wv.stepsovc.utils.ConstantUtils;
import org.wv.stepsovc.vo.BeneficiaryFormRequest;

import java.util.HashMap;
import java.util.Map;


@Component
public class CommcareGateway {

    HttpClientService httpClientService;

    VelocityEngine velocityEngine;

    HttpClientEventListener httpClientEventListener;

    Map model;

    private static final String BENEFICIARY_FORM_TEMPLATE_PATH = "/templates/beneficiary-form.xml";

    private static final String OWNER_UPDATE_FORM_TEMPLATE_PATH = "/templates/update-owner-form.xml";

    @Autowired
    public CommcareGateway(HttpClientService httpClientService, VelocityEngine velocityEngine, HttpClientEventListener httpClientEventListener) {
        this.httpClientService = httpClientService;
        this.velocityEngine = velocityEngine;
        this.httpClientEventListener = httpClientEventListener;
    }

    public void submitNewBeneficiaryForm(String url, BeneficiaryFormRequest beneficiaryFormRequest) {
        model = new HashMap<String, Object>();
        model.put(ConstantUtils.BENEFICIARY, beneficiaryFormRequest);
        httpClientService.post(url, getXmlFromObject(BENEFICIARY_FORM_TEMPLATE_PATH, model));
    }

    public void submitOwnerUpdateForm(String url,  BeneficiaryFormRequest beneficiaryFormRequest) {
        model = new HashMap<String, Object>();
        model.put(ConstantUtils.BENEFICIARY, beneficiaryFormRequest);
        String xmlFromObject = getXmlFromObject(OWNER_UPDATE_FORM_TEMPLATE_PATH, model);
        httpClientService.post(url, xmlFromObject);
    }


    public String getXmlFromObject(String templatePath, Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, "UTF-8", model);
    }
}
