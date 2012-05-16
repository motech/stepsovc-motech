package org.wv.stepsovc.web.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.utils.DateUtils;
import org.wv.stepsovc.web.domain.Facility;
import org.wv.stepsovc.web.domain.ServiceUnavailability;
import org.wv.stepsovc.web.repository.AllFacilities;
import org.wv.stepsovc.web.request.StepsovcCase;
import org.wv.stepsovc.web.vo.FacilityAvailability;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FacilityService {

    private static Logger logger = Logger.getLogger(FacilityService.class);

    @Autowired
    private AllFacilities allFacilities;
    @Autowired
    private ReferralService referralService;

    public void makeFacilityUnavailable(StepsovcCase facilityCase) {
        String nextAvailableDate = null;
        String serviceUnavailableTo = facilityCase.getService_unavailable_to();
        Facility facility = allFacilities.findFacilityByCode(facilityCase.getFacility_code());

        ServiceUnavailability serviceUnavailability = new ServiceUnavailability(facilityCase.getService_unavailable_reason(), facilityCase.getService_unavailable_from(), serviceUnavailableTo);
        if (facility == null) {
            List<ServiceUnavailability> serviceUnavailabilities = Arrays.asList(serviceUnavailability);
            facility = new Facility(facilityCase.getFacility_code(), facilityCase.getFacility_name(), serviceUnavailabilities, null); // Todo: remove this if block
            allFacilities.add(facility);
        } else {
            facility.getServiceUnavailabilities().add(serviceUnavailability);
            allFacilities.update(facility);
        }

        try {
            nextAvailableDate = getFacilityAvailability(DateUtils.nextDateStr(serviceUnavailableTo), facility).getNextAvailableDate();
        } catch (ParseException e) {
        }
        referralService.updateReferralsServiceDate(facilityCase.getFacility_code(), facilityCase.getService_unavailable_from(), facilityCase.getService_unavailable_to(), nextAvailableDate);
    }

    public FacilityAvailability getFacilityAvailability(String facilityId, String serviceDateStr) {

        Facility facility = allFacilities.findFacilityByCode(facilityId);
        return facility == null ? new FacilityAvailability(true, null) : getFacilityAvailability(serviceDateStr, facility);
    }

    private FacilityAvailability getFacilityAvailability(String serviceDateStr, Facility facility) {
        boolean isAvailable = true;

        Date serviceDate = null;
        try {
            serviceDate = DateUtils.getDate(serviceDateStr);
            Collections.sort(facility.getServiceUnavailabilities());

            for (ServiceUnavailability serviceUnavailability : facility.getServiceUnavailabilities()) {
                Date fromDate = DateUtils.getDate(serviceUnavailability.getFromDate());
                Date toDate = DateUtils.getDate(serviceUnavailability.getToDate());

                if ((serviceDate.equals(fromDate) || serviceDate.equals(toDate)) ||
                        (serviceDate.after(fromDate) && serviceDate.before(toDate))) {
                    serviceDate = DateUtils.nextDate(toDate);
                    isAvailable = false;
                } else if (!isAvailable) {
                    break;
                }
            }
        } catch (ParseException e) {
        }
        return new FacilityAvailability(isAvailable, DateUtils.getFormattedDate(serviceDate));
    }
}
