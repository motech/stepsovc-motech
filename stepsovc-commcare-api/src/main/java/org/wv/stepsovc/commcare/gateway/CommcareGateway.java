package org.wv.stepsovc.commcare.gateway;

import org.apache.velocity.app.VelocityEngine;
import org.motechproject.http.client.listener.HttpClientEventListener;
import org.motechproject.http.client.service.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.wv.stepsovc.commcare.domain.Group;
import org.wv.stepsovc.commcare.factories.GroupFactory;
import org.wv.stepsovc.commcare.repository.AllGroups;
import org.wv.stepsovc.commcare.repository.AllUsers;
import org.wv.stepsovc.commcare.vo.BeneficiaryInformation;
import org.wv.stepsovc.commcare.vo.CareGiverInformation;

import java.util.HashMap;
import java.util.Map;


@Component
public class CommcareGateway {

    public static final String BENEFICIARY_CASE_FORM_TEMPLATE_PATH = "/templates/beneficiary-case-form.xml";

    public static final String OWNER_UPDATE_FORM_TEMPLATE_PATH = "/templates/update-owner-form.xml";

    public static final String USER_REGISTRATION_FORM_TEMPLATE_PATH = "/templates/user-registration-form.xml";

    public static final String BENEFICIARY_FORM_KEY = "beneficiary";

    public static final String CARE_GIVER_FORM_KEY = "caregiver";
    @Autowired
    private HttpClientService httpClientService;
    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private HttpClientEventListener httpClientEventListener;
    @Autowired
    private AllGroups allGroups;
    @Autowired
    private AllUsers allUsers;
    private Map model;

    public static final String COMMCARE_URL = "http://localhost:8000/a/stepsovc/receiver";

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


    public void createNewBeneficiary(BeneficiaryInformation beneficiaryInformation) {
        model = new HashMap<String, Object>();
        model.put(BENEFICIARY_FORM_KEY, beneficiaryInformation);
        httpClientService.post(COMMCARE_URL, getXmlFromObject(BENEFICIARY_CASE_FORM_TEMPLATE_PATH, model));
    }

    public void updateReferralOwner(BeneficiaryInformation beneficiaryInformation) {
        model = new HashMap<String, Object>();
        model.put(BENEFICIARY_FORM_KEY, beneficiaryInformation);
        httpClientService.post(COMMCARE_URL, getXmlFromObject(OWNER_UPDATE_FORM_TEMPLATE_PATH, model));
    }


    public void registerUser(CareGiverInformation careGiverInformation) {
        model = new HashMap<String, Object>();
        model.put(CARE_GIVER_FORM_KEY, careGiverInformation);
        httpClientService.post(COMMCARE_URL, getXmlFromObject(USER_REGISTRATION_FORM_TEMPLATE_PATH, model));
    }

    String getXmlFromObject(String templatePath, Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, "UTF-8", model);
    }
}
