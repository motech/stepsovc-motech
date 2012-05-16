package org.wv.stepsovc.commcare.gateway;

import org.apache.velocity.app.VelocityEngine;
import org.motechproject.http.client.listener.HttpClientEventListener;
import org.motechproject.http.client.service.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.wv.stepsovc.commcare.domain.CaseType;
import org.wv.stepsovc.commcare.domain.Group;
import org.wv.stepsovc.commcare.factories.GroupFactory;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.commcare.repository.AllUsers;
import org.wv.stepsovc.vo.BeneficiaryInformation;

import java.util.HashMap;
import java.util.Map;


@Component
public class CommcareGateway {

    private static final String BENEFICIARY_FORM_TEMPLATE_PATH = "/templates/beneficiary-case-form.xml";

    private static final String OWNER_UPDATE_FORM_TEMPLATE_PATH = "/templates/update-owner-form.xml";

    private HttpClientService httpClientService;

    private VelocityEngine velocityEngine;

    private HttpClientEventListener httpClientEventListener;

    private Map model;

    private AllGroups allGroups;

    private AllUsers allUsers;


    @Autowired
    public CommcareGateway(HttpClientService httpClientService, VelocityEngine velocityEngine, HttpClientEventListener httpClientEventListener, AllGroups allGroups, AllUsers allUsers) {
        this.httpClientService = httpClientService;
        this.velocityEngine = velocityEngine;
        this.httpClientEventListener = httpClientEventListener;
        this.allGroups = allGroups;
        this.allUsers = allUsers;
    }

    public String getUserId(String name) {
        return allUsers.getUserByName(name) == null ? null : allUsers.getUserByName(name).getId();
    }

    public String getGroupId(String name) {
        return allGroups.getGroupByName(name) == null ? null : allGroups.getGroupByName(name).getId();
    }

    public boolean createGroup(String groupName, String[] commcareUserIds, String domain) {

        if (allGroups.getGroupByName(groupName) != null)
            return false;

        Group newGroup = GroupFactory.createGroup(groupName, commcareUserIds, domain);
        allGroups.add(newGroup);

        return true;
    }


    public void createNewBeneficiary(String url, BeneficiaryInformation beneficiaryInformation) {
        model = new HashMap<String, Object>();
        model.put(CaseType.BENEFICIARY_CASE.getType(), beneficiaryInformation);
        httpClientService.post(url, getXmlFromObject(BENEFICIARY_FORM_TEMPLATE_PATH, model));
    }

    public void updateReferralOwner(String url, BeneficiaryInformation beneficiaryInformation) {
        model = new HashMap<String, Object>();
        model.put(CaseType.BENEFICIARY_CASE.getType(), beneficiaryInformation);
        String xmlFromObject = getXmlFromObject(OWNER_UPDATE_FORM_TEMPLATE_PATH, model);
        httpClientService.post(url, xmlFromObject);
    }


    String getXmlFromObject(String templatePath, Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, "UTF-8", model);
    }
}
