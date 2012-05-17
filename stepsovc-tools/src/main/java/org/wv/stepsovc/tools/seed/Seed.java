package org.wv.stepsovc.tools.seed;

import org.slf4j.LoggerFactory;

public abstract class Seed {
    org.slf4j.Logger LOG = LoggerFactory.getLogger(this.getClass());

    public void run() {
        preLoad();
        load();
        postLoad();
    }

    protected void postLoad() {
        LOG.info("Seed finished.");
    }

    protected void preLoad() {
        LOG.info("Seed started.");
    }

    protected abstract void load();
}