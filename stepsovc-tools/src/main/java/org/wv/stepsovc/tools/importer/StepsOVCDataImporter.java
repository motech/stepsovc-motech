package org.wv.stepsovc.tools.importer;

import org.motechproject.importer.CSVDataImporter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StepsOVCDataImporter {
    public static final String APPLICATION_CONTEXT_XML = "applicationContext-Tools.xml";

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        CSVDataImporter dataImporter = (CSVDataImporter) context.getBean("csvDataImporter");
        dataImporter.importData(args[0], args[1]);
    }

}
