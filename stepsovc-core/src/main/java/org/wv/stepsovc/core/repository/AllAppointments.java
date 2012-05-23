package org.wv.stepsovc.core.repository;

import org.motechproject.appointments.api.service.AppointmentService;
import org.motechproject.appointments.api.service.contract.CreateVisitRequest;
import org.motechproject.appointments.api.service.contract.VisitResponse;
import org.motechproject.appointments.api.service.contract.VisitsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllAppointments {

    @Autowired
    AppointmentService appointmentService;

    static final int ONE_DAY_BEFORE = -1;
    static final int ONE_DAY_LATER = 1;

    public void add(String externalId, CreateVisitRequest createVisitRequest) {
        appointmentService.addVisit(externalId, createVisitRequest);
    }

    private int switchSign(int days) {
        return -days;
    }

    public List<VisitResponse> find(VisitsQuery visitsQuery) {
        return appointmentService.search(visitsQuery);
    }

    public void remove(String ovcId) {
        appointmentService.removeCalendar(ovcId);
    }
}
