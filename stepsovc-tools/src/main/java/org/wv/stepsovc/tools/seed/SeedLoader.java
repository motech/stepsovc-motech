package org.wv.stepsovc.tools.seed;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SeedLoader {
    Logger LOG = Logger.getLogger(this.getClass());
    private List<Seed> seeds;

    public SeedLoader(List<Seed> seeds) {
        this.seeds = seeds;
    }

    public SeedLoader() {
    }

    public void load() {
        LOG.info("Started loading seeds :" + seeds.toString());
        for (Seed seed : seeds) {
            try {
                seed.run();
            } catch (Exception e) {
                LOG.error("Encountered error while loading seed, " + seed.getClass().getName(), e);
            }
        }
    }

    public static final String APPLICATION_CONTEXT_XML = "applicationContext-Tools.xml";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_XML);
        SeedLoader seedLoader = (SeedLoader) context.getBean("seedLoader");
        seedLoader.load();
        ((ClassPathXmlApplicationContext) context).close();
    }
}
