package org.webcrawler.model.statistic;

import java.util.Map;

public class TermStatistic extends Statistic {

    private Map<String, Long> termsHints;

    public TermStatistic(String seed, Map<String, Long> termsHints) {
        super(seed);
        this.termsHints = termsHints;
    }

    public Map<String, Long> getTermsHints() {
        return termsHints;
    }

    public void setTermsHints(Map<String, Long> termsHints) {
        this.termsHints = termsHints;
    }

    @Override
    public String toString() {
        return "TermStatistic{" +
                "termsHints=" + termsHints +
                ", seed='" + seed + '\'' +
                '}';
    }
}
