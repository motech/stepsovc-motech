package org.wv.stepsovc.tools.seed;

import org.apache.log4j.Logger;

public abstract class Seed {

    private Logger logger = Logger.getLogger(this.getClass());

    public void run() {
        preLoad();
        load();
        postLoad();
    }

    protected void postLoad() {
        logger.info("Seed finished.");
    }

    protected void preLoad() {
        logger.info("Seed started.");
    }

    protected abstract void load();
}