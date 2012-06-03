package org.wv.stepsovc.core.repository;

import org.apache.commons.collections.CollectionUtils;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.core.domain.Beneficiary;

import java.util.List;

@Repository
public class AllBeneficiaries extends MotechBaseRepository<Beneficiary> {

    @Autowired
    protected AllBeneficiaries(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Beneficiary.class, dbCouchDbConnector);
    }

    @View(name = "by_beneficiary_code", map = "function(doc){ if(doc.type == 'Beneficiary') emit([doc.code],doc) }")
    public Beneficiary findBeneficiaryByCode(String beneficiaryCode) {
        ViewQuery viewQuery = createQuery("by_beneficiary_code").key(ComplexKey.of(beneficiaryCode)).includeDocs(true);
        List<Beneficiary> beneficiaries = db.queryView(viewQuery, Beneficiary.class);
        return CollectionUtils.isEmpty(beneficiaries) ? null : beneficiaries.get(0);
    }

}
