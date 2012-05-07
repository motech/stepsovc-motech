package org.wv.stepsovc.commcare.repository;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.commcare.domain.Group;

import java.util.List;

@Repository
public class AllGroups extends MotechBaseRepository<Group> {

    @Autowired
    protected AllGroups(@Qualifier("commcareDbConnector") CouchDbConnector dbCouchDbConnector) {
        super(Group.class, dbCouchDbConnector);
    }

    @View(name = "by_name", map = "function(doc){ \n" +
            "    if (doc.doc_type == \"Group\") {\n" +
            "        emit([doc.domain, doc.name],  null);\n" +
            "    }\n" +
            "}")
    public Group getGroupByName(String name) {
        ViewQuery viewQuery = createQuery("by_name").key(ComplexKey.of("stepsovc",name)).includeDocs(true);
        List<Group> groups = db.queryView(viewQuery, Group.class);
        return groups.size() > 0 ? groups.get(0) : null;
    }

    public void removeByName(String name) {
        remove(getGroupByName(name));
    }
}
