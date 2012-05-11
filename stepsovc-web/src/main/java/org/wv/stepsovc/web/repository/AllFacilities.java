package org.wv.stepsovc.web.repository;

import org.apache.commons.collections.CollectionUtils;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.web.domain.Facility;

import java.util.List;

@Repository
public class AllFacilities extends MotechBaseRepository<Facility> {

    @Autowired
    protected AllFacilities(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Facility.class, dbCouchDbConnector);
    }

    @View(name = "by_facilityCode", map = "function(doc){ if(doc.type === 'Facility') emit([doc.facilityCode],doc) }")
    public Facility findFacilityByCode(String facilityCode) {
        ViewQuery viewQuery = createQuery("by_facilityCode").key(ComplexKey.of(facilityCode)).includeDocs(true);
        List<Facility> facilities = db.queryView(viewQuery, Facility.class);
        return CollectionUtils.isEmpty(facilities) ? null : facilities.get(0);
    }


}
