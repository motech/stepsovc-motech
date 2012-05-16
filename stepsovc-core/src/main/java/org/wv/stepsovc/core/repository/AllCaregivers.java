package org.wv.stepsovc.core.repository;

import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.core.domain.Caregiver;

@Repository
public class AllCaregivers extends MotechBaseRepository<Caregiver>{

    @Autowired
    protected AllCaregivers(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Caregiver.class, dbCouchDbConnector);
    }
}
