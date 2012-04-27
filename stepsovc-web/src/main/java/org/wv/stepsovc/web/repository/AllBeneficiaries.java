package org.wv.stepsovc.web.repository;

import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.web.domain.Beneficiary;

@Repository
public class AllBeneficiaries extends MotechBaseRepository<Beneficiary> {

    @Autowired
    protected AllBeneficiaries(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Beneficiary.class, dbCouchDbConnector);
    }
}