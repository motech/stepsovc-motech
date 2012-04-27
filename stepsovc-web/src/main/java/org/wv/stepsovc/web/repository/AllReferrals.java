package org.wv.stepsovc.web.repository;

import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.web.domain.Referral;

@Repository
public class AllReferrals extends MotechBaseRepository<Referral>{
    @Autowired
    protected AllReferrals(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Referral.class, dbCouchDbConnector);
    }
}
