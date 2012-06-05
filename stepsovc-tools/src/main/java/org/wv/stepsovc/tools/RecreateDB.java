package org.wv.stepsovc.tools;

import org.apache.log4j.Logger;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RecreateDB {
    public static final String APPLICATION_CONTEXT_XML = "applicationContext-Tools.xml";
    private static Logger logger = Logger.getLogger(RecreateDB.class);

    @Autowired
    @Qualifier("couchDbInstance")
    private CouchDbInstance couchDbInstance;
    @Autowired
    private CouchDbConnector cmsLiteDatabase;

    public static void main(String[] args) {
        logger.info("Recreate DB: START");
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        RecreateDB recreateDB = context.getBean(RecreateDB.class);
        recreateDB.recreateAllDB(recreateDB);
        ((ClassPathXmlApplicationContext) context).close();
        logger.info("Recreate DB: END");
    }

    private void recreateAllDB(RecreateDB recreateDB) {
        recreateDB.recreate(cmsLiteDatabase.getDatabaseName());
    }

    private void recreate(String dbName) {
        couchDbInstance.deleteDatabase(dbName);
        couchDbInstance.createDatabase(dbName);
    }
}
