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
import org.wv.stepsovc.core.domain.Caregiver;

import java.util.List;

@Repository
public class AllCaregivers extends MotechBaseRepository<Caregiver> {

    @Autowired
    protected AllCaregivers(@Qualifier("stepsovcDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Caregiver.class, dbCouchDbConnector);
    }

    @View(name = "by_caregiver_code", map = "function(doc){ if(doc.type == 'Caregiver') emit([doc.code],doc) }")
    public Caregiver findCaregiverByCode(String caregiverCode) {
        ViewQuery viewQuery = createQuery("by_caregiver_code").key(ComplexKey.of(caregiverCode)).includeDocs(true);
        List<Caregiver> caregivers = db.queryView(viewQuery, Caregiver.class);
        return CollectionUtils.isEmpty(caregivers) ? null : caregivers.get(0);
    }

    @View(name = "by_caregiver_id", map = "function(doc){ if(doc.type == 'Caregiver') emit([doc.cgId],doc) }")
    public Caregiver findCaregiverById(String caregiverId) {
        ViewQuery viewQuery = createQuery("by_caregiver_id").key(ComplexKey.of(caregiverId)).includeDocs(true);
        List<Caregiver> caregivers = db.queryView(viewQuery, Caregiver.class);
        return CollectionUtils.isEmpty(caregivers) ? null : caregivers.get(0);
    }


}
