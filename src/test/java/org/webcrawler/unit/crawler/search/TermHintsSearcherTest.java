package org.webcrawler.unit.crawler.search;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.TermHintsSearcher;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.model.statistic.TermStatistic;
import org.webcrawler.unit.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TermHintsSearcherTest {

    private CrawlSearcher termsSearcher;
    private static final Map<CrawlingSeed, Page> details;
    private static final int DEFAULT_LIMIT = 100;
    private static final List<String> inputTerms = Arrays.asList("minsk", StringUtil.PARAGRAPH_TERM);

    static {
        details = Map.of(
                new CrawlingSeed(StringUtil.DEFAULT_SEED, 0),
                new Page(Collections.emptyList(), StringUtil.TEST_HTML_STRING),
                new CrawlingSeed("http://second.html", 1),
                new Page(Collections.emptyList(), StringUtil.TEST_HTML_STRING)
        );
    }

    @Before
    public void setUp() {
        termsSearcher = new TermHintsSearcher(inputTerms);
    }

    @Test
    public void shouldBeFoundSpecifiedTerm() {
        List<String> actualFoundTerm = findTerm(details, StringUtil.PARAGRAPH_TERM);
        assertTrue(isTermEqualToAllAnotherTerms(actualFoundTerm, StringUtil.PARAGRAPH_TERM));
    }

    @Test
    public void shouldBeFoundSpecifiedTermTwoTimes() {
        List<String> actualFoundTerm = findTerm(details, StringUtil.PARAGRAPH_TERM);
        assertEquals(2, actualFoundTerm.size());
    }

    @Test
    public void shouldReturnOneSize_whenLimitIsOne() {
        List<Statistic> statistics = termsSearcher.search(details).limit(1);
        assertEquals(1, statistics.size());
    }

    @Test
    public void shouldReturnAll_whenLimitIsZero() {
        List<Statistic> statistics = termsSearcher.search(details).limit(TermHintsSearcher.DISPLAY_ALL_STATISTICS);
        assertEquals(details.size(), statistics.size());
    }

    @Test
    public void shouldReturnAllStatistics_whenLimitIsZero() {
        List<Statistic> statistics = termsSearcher.search(details).limit(TermHintsSearcher.DISPLAY_ALL_STATISTICS);
        boolean equals = statistics.get(0).getSeed().equals(StringUtil.DEFAULT_SEED) ||
                statistics.get(1).getSeed().equals(StringUtil.DEFAULT_SEED);
        assertTrue(equals);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenLimitLessThanZero() {
        termsSearcher.limit(-1);
    }

    public boolean isTermEqualToAllAnotherTerms(List<String> terms, String term) {
        for (String t: terms) {
            if (!t.equals(term)) {
                return false;
            }
        }
        return true;
    }

    private List<String> findTerm(Map<CrawlingSeed, Page> details, String termToBeFind) {
        return termsSearcher
                .search(details)
                .limit(DEFAULT_LIMIT).stream()
                .flatMap(map -> ((TermStatistic) map).getTermsHints().entrySet().stream())
                .filter(term -> term.getKey().equals(termToBeFind))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}