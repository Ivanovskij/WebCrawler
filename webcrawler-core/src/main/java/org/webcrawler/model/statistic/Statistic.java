package org.webcrawler.model.statistic;

/**
 * Parent statistic
 * You can easily create a new type of statistics that you need
 */
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
