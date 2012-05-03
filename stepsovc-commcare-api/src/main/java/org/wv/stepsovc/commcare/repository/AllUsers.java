package org.wv.stepsovc.commcare.repository;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wv.stepsovc.commcare.domain.User;

import java.util.List;

@Repository
public class AllUsers  extends MotechBaseRepository<User> {

    @Autowired
    protected AllUsers(@Qualifier("commcareDbConnector")  CouchDbConnector db) {
        super(User.class, db);
    }

    @View(name = "by_username", map = "function(doc) {\n" +
            "    if(doc.base_doc === 'CouchUser') {\n" +
            "        emit([doc.username], doc);\n" +
            "    }\n" +
            "}")
    public User getUserByName(String name) {
        ViewQuery viewQuery = createQuery("by_username").key(ComplexKey.of(name)).includeDocs(true);
        List<User> users = db.queryView(viewQuery, User.class);
        return users.size() > 0 ? users.get(0) : null;
    }

    public String getIdByName(String name) {
        return getUserByName(name) == null ? null : getUserByName(name).getId();
    }

    public void removeByName(String name) {
        remove(getUserByName(name));
    }
}
