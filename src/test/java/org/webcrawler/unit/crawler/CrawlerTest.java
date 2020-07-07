package org.webcrawler.unit.crawler;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.unit.crawler.search.CrawlSearcher;
import org.webcrawler.unit.crawler.search.DefaultSearcher;
import org.webcrawler.unit.crawler.search.SortDirection;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.unit.util.StringUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CrawlerTest {

    private DefaultCrawler defaultCrawler;

    @Before
    public void setUp() {
        defaultCrawler = new DefaultCrawler();
    }

    @Test
    public void shouldCrawlWithHelPOfDefaultSearcher() {
        CrawlSearcher actualSearcher = defaultCrawler.crawl(StringUtil.DEFAULT_SEED, 0);
        assertTrue(actualSearcher instanceof DefaultSearcher);
    }

    @Test
    public void shouldReturnSimilarSize_whenSearcherIsDefault() {
        List<Statistic> actual = defaultCrawler.crawl(StringUtil.DEFAULT_SEED, 0)
                .sort(SortDirection.DESC)
                .limit(10);
        assertEquals(0, actual.size());
    }

    @Test
    public void shouldReturnEmptyStatistic_whenSearcherIsDefault() {
        List<Statistic> actual = defaultCrawler.crawl(StringUtil.DEFAULT_SEED, 0)
                .sort(SortDirection.DESC)
                .limit(10);
        assertEquals(Collections.emptyList().toString(), actual.toString());
    }

    private static class DefaultCrawler implements Crawler {
        @Override
        public CrawlSearcher crawl(String rootSeed, int depth, CrawlSearcher crawlSearcher) {
            Map<CrawlingSeed, Page> details = new HashMap<>();
            details.put(new CrawlingSeed(StringUtil.DEFAULT_SEED, 0),
                    new Page(Collections.singletonList(""), ""));
            return crawlSearcher.search(details);
        }
    }

}