package org.wv.stepsovc.web.repository;

import org.ektorp.CouchDbConnector;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.web.request.BeneficiaryCase;

/**
 * Created with IntelliJ IDEA.
 * User: sathishkumar
 * Date: 23/04/12
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AllBeneficiaries extends MotechBaseRepository<BeneficiaryCase> {

    @Autowired
    protected AllBeneficiaries(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(BeneficiaryCase.class, dbCouchDbConnector);
    }


}
