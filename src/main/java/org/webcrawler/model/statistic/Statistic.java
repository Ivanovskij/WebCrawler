package org.webcrawler.model.statistic;

public abstract class Statistic {

    protected String seed;

    public Statistic(String seed) {
        this.seed = seed;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}
