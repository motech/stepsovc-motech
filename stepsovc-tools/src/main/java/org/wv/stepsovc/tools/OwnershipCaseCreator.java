package org.wv.stepsovc.tools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;

public class OwnershipCaseCreator {

    private static final String APPLICATION_CONTEXT_XML = "classpath:applicationContext-stepsovc-commcare-api.xml";
    public static final String COMMCARE_GATEWAY = "commcareGateway";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        CommcareGateway commcareGateway = (CommcareGateway) context.getBean(COMMCARE_GATEWAY);
        commcareGateway.createOwnershipCase();
    }
}
