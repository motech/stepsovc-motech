package org.wv.stepsovc.core.repository;

import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.core.domain.HealthWorker;

@Repository
public class AllHealthWorkers extends MotechBaseRepository<HealthWorker> {
    @Autowired
    protected AllHealthWorkers(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(HealthWorker.class, dbCouchDbConnector);
    }
}
