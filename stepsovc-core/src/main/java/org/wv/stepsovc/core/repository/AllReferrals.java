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
import org.wv.stepsovc.core.domain.Referral;

import java.util.List;

@Repository
public class AllReferrals extends MotechBaseRepository<Referral> {

    @Autowired
    protected AllReferrals(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Referral.class, dbCouchDbConnector);
    }

    @View(name = "active_by_beneficiary", map = "function(doc){ if(doc.type == 'Referral' && doc.active == true) emit([doc.beneficiaryCode],doc) }")
    public Referral findActiveReferral(String beneficiaryCode) {
        ViewQuery viewQuery = createQuery("active_by_beneficiary").key(ComplexKey.of(beneficiaryCode)).includeDocs(true);
        List<Referral> latestReferral = db.queryView(viewQuery, Referral.class);
        return CollectionUtils.isEmpty(latestReferral) ? null : latestReferral.get(0);
    }

    @View(name = "active_by_ovc_id", map = "function(doc){ if(doc.type == 'Referral' && doc.active == true) emit(doc.ovcId,doc) }")
    public Referral findActiveByOvcId(String ovcId) {
        ViewQuery viewQuery = createQuery("active_by_ovc_id").key(ovcId).includeDocs(true);
        List<Referral> latestReferral = db.queryView(viewQuery, Referral.class);
        return CollectionUtils.isEmpty(latestReferral) ? null : latestReferral.get(0);
    }

    @View(name = "by_facility_and_service_date", map = "function(doc){ if(doc.type == 'Referral'  && doc.active == true) emit([doc.facilityCode, doc.serviceDate],doc) }")
    public List<Referral> findActiveReferrals(String facilityCode, String date) {
        ViewQuery viewQuery = createQuery("by_facility_and_service_date").key(ComplexKey.of(facilityCode, date)).includeDocs(true);
        List<Referral> referrals = db.queryView(viewQuery, Referral.class);
        return referrals;
    }

    @View(name = "all_by_beneficiary", map = "function(doc){ if(doc.type == 'Referral') emit([doc.beneficiaryCode],doc) }")
    public void removeAllByBeneficiary(String beneficiaryCode) {
        ViewQuery viewQuery = createQuery("all_by_beneficiary").key(ComplexKey.of(beneficiaryCode)).includeDocs(true);
        List<Referral> allReferrals = db.queryView(viewQuery, Referral.class);
        for (Referral referral : allReferrals)
            this.remove(referral);
    }
}

