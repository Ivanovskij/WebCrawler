package org.webcrawler.model;

public class CrawlingSeed {
    private String seed;
    private int depth;

    public CrawlingSeed(String seed, int depth) {
        this.seed = seed;
        this.depth = depth;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "CrawlingSeed{" +
                "seed='" + seed + '\'' +
                ", depth=" + depth +
                '}';
    }
}
