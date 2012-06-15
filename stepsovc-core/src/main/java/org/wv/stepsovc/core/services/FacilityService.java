package org.wv.stepsovc.core.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wv.stepsovc.commcare.gateway.CommcareGateway;
import org.wv.stepsovc.core.domain.Facility;
import org.wv.stepsovc.core.domain.Referral;
import org.wv.stepsovc.core.domain.ServiceUnavailability;
import org.wv.stepsovc.core.mapper.BeneficiaryMapper;
import org.wv.stepsovc.core.mapper.FacilityMapper;
import org.wv.stepsovc.core.repository.AllFacilities;
import org.wv.stepsovc.core.request.StepsovcCase;
import org.wv.stepsovc.core.vo.FacilityAvailability;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Collections.sort;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.wv.stepsovc.core.utils.DateUtils.*;

public class FacilityService {

    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private AllFacilities allFacilities;
    @Autowired
    private ReferralService referralService;
    @Autowired
    private StepsovcAlertService stepsovcAlertService;
    @Autowired
    private CommcareGateway commcareGateway;
    @Autowired
    private FacilityMapper facilityMapper;


    public void makeFacilityUnavailable(StepsovcCase facilityCase) {
        String facilityCode = facilityCase.getFacility_code();
        String serviceUnavailableFrom = facilityCase.getService_unavailable_from();
        String serviceUnavailableTo = facilityCase.getService_unavailable_to();
        String nextAvailableDate = null;
        Facility facility = allFacilities.findFacilityByCode(facilityCode);

        ServiceUnavailability serviceUnavailability = new ServiceUnavailability(facilityCase.getService_unavailable_reason(), serviceUnavailableFrom, serviceUnavailableTo);
        if (facility == null) {
            List<ServiceUnavailability> serviceUnavailabilities = Arrays.asList(serviceUnavailability);
            facility = new Facility(facilityCode, facilityCase.getFacility_name(), serviceUnavailabilities, null); // Todo: remove this if block
            allFacilities.add(facility);
        } else {
            facility.getServiceUnavailabilities().add(serviceUnavailability);
            allFacilities.update(facility);
        }

        try {
            nextAvailableDate = getFacilityAvailability(nextDateStr(serviceUnavailableTo), facility).getNextAvailableDate();
        } catch (ParseException e) {
            logger.error("Date Parse Exception ", e);
        }
        List<Referral> updatedReferrals = referralService.updateReferralsServiceDate(facilityCode, serviceUnavailableFrom, serviceUnavailableTo, nextAvailableDate);
        stepsovcAlertService.sendInstantServiceUnavailabilityMsgToCareGivers(updatedReferrals, facilityCode, serviceUnavailableFrom, serviceUnavailableTo, nextAvailableDate);
    }

    public FacilityAvailability getFacilityAvailability(String facilityId, String serviceDateStr) {

        Facility facility = allFacilities.findFacilityByCode(facilityId);
        return facility == null ? new FacilityAvailability(true) : getFacilityAvailability(serviceDateStr, facility);
    }

    private FacilityAvailability getFacilityAvailability(String serviceDateStr, Facility facility) {

        boolean isAvailable = true;
        Date serviceDate;
        String unavailableToDate = null;
        String unavailableFromDate = null;
        serviceDate = getDate(serviceDateStr);
        List<ServiceUnavailability> serviceUnavailabilities = new ArrayList<ServiceUnavailability>(facility.getServiceUnavailabilities());
        if (isNotEmpty(serviceUnavailabilities)) {
            sort(serviceUnavailabilities);
            for (ServiceUnavailability serviceUnavailability : serviceUnavailabilities) {
                Date fromDate = getDate(serviceUnavailability.getFromDate());
                Date toDate = getDate(serviceUnavailability.getToDate());

                if ((serviceDate.equals(fromDate) || serviceDate.equals(toDate)) ||
                        (serviceDate.after(fromDate) && serviceDate.before(toDate))) {
                    serviceDate = nextDate(toDate);
                    isAvailable = false;
                    unavailableFromDate = serviceUnavailability.getFromDate();
                    unavailableToDate = serviceUnavailability.getToDate();
                } else if (!isAvailable) {
                    break;
                }
            }
        }
        return new FacilityAvailability(isAvailable, getFormattedDate(serviceDate), unavailableFromDate, unavailableToDate);
    }

    public void registerFacility(StepsovcCase stepsovcCase) {
        commcareGateway.addGroupOwnership(new BeneficiaryMapper().createOwnershipInfo(stepsovcCase), CommcareGateway.ALL_USERS_GROUP);
    }


    public void addFacility(Facility facility) {
        Facility existingFacility = allFacilities.findFacilityByCode(facility.getFacilityCode());
        if (existingFacility == null) {
            allFacilities.add(facility);
        } else {
            allFacilities.update(facilityMapper.map(existingFacility, facility));
        }
    }
}
