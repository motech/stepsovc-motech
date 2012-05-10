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
import java.util.Date;
import java.util.List;

public class FacilityService {

    private static Logger logger = Logger.getLogger(FacilityService.class);

    @Autowired
    private AllFacilities allFacilities;

    public FacilityAvailability getFacilityAvailability(String facilityId, String serviceDateStr) {

        Facility facility = allFacilities.findFacilityById(facilityId);
        return facility == null ? new FacilityAvailability(true, null ) : getFacilityAvailability(serviceDateStr, facility);
    }

    public void makeFacilityUnavailable(StepsovcCase facilityCase) {
        Facility facility = allFacilities.findFacilityById(facilityCase.getFacility_id());

        ServiceUnavailability serviceUnavailability = new ServiceUnavailability(facilityCase.getService_unavailable_reason(), facilityCase.getService_unavailable_from(), facilityCase.getService_unavailable_to());
        if(facility == null) {
            List<ServiceUnavailability> serviceUnavailabilities = Arrays.asList(serviceUnavailability);
            facility = new Facility(facilityCase.getFacility_id(), facilityCase.getFacility_name(), serviceUnavailabilities);
            allFacilities.add(facility);
        } else {
            //TODO: check for overlapping unavailable dates
            facility.getServiceUnavailabilities().add(serviceUnavailability);

            allFacilities.update(facility);
        }
    }

    private FacilityAvailability getFacilityAvailability(String serviceDateStr, Facility facility) {
        try {
            Date serviceDate = DateUtils.getDate(serviceDateStr);

            for(ServiceUnavailability serviceUnavailability : facility.getServiceUnavailabilities()) {
                Date fromDate = DateUtils.getDate(serviceUnavailability.getFromDate());
                Date toDate = DateUtils.getDate(serviceUnavailability.getToDate());

                if((serviceDate.equals(fromDate) || serviceDate.equals(toDate)) ||
                        (serviceDate.after(fromDate) && serviceDate.before(toDate)))
                    return new FacilityAvailability(false, DateUtils.nextDate(toDate));
            }
        } catch (ParseException e) {
        }
        return new FacilityAvailability(true, null);
    }
}
