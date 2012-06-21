package fixture;

import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.Service;
import org.wv.stepsovc.core.domain.ServiceType;

import java.util.HashMap;
import java.util.Map;

public class ReferralFixture {

    public static Referral getReferral() {
        Referral referral = new Referral();
        referral.setOvcId("referralId");
        referral.setCgId("cgId");
        referral.setFacilityCode("facCode");
        referral.setFollowupRequired("No");
        referral.setServiceDate("2012-12-12");
        referral.setServiceDetails("visit");
        referral.setReferredServices(getServicesMap());
        return referral;
    }

    private static Map<String, Service> getServicesMap() {
        HashMap<String, Service> serviceHashMap = new HashMap<String, Service>();
        serviceHashMap.put(ServiceType.ART_ADHERENCE.getCode(), new Service(true, true, ServiceType.ART_ADHERENCE));
        serviceHashMap.put(ServiceType.CONDOMS.getCode(), new Service(true, true, ServiceType.CONDOMS));
        serviceHashMap.put(ServiceType.END_OF_LIFE_HOSPICE.getCode(), new Service(true, false, ServiceType.END_OF_LIFE_HOSPICE));
        serviceHashMap.put(ServiceType.FAMILY_PLANNING.getCode(), new Service(true, false, ServiceType.FAMILY_PLANNING));
        serviceHashMap.put(ServiceType.HIV_COUNSELING.getCode(), new Service(false, true, ServiceType.HIV_COUNSELING));
        serviceHashMap.put(ServiceType.NEW_DIAGNOSIS.getCode(), new Service(false, true, ServiceType.NEW_DIAGNOSIS));
        serviceHashMap.put(ServiceType.OTHER_HEALTH_SERVICES.getCode(), new Service(false, false, ServiceType.OTHER_HEALTH_SERVICES));
        serviceHashMap.put(ServiceType.PAIN_MANAGEMENT.getCode(), new Service(false, false, ServiceType.PAIN_MANAGEMENT));
        serviceHashMap.put(ServiceType.PMTCT.getCode(), new Service(true, true, ServiceType.PMTCT));
        serviceHashMap.put(ServiceType.SEXUALLY_TRANSMITTED_INFEC.getCode(), new Service(true, true, ServiceType.SEXUALLY_TRANSMITTED_INFEC));
        return serviceHashMap;
    }

}
