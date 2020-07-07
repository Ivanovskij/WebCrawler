package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * No any searches
 * If crawl searches was not specified
 */
public class DefaultSearcher implements CrawlSearcher {

    @Override
    public CrawlSearcher search(Map<CrawlingSeed, Page> details) {
        return this;
    }

    @Override
    public CrawlSearcher sort(SortDirection direction) {
        return this;
    }

    @Override
    public List<Statistic> limit(long limit) {
        return Collections.emptyList();
    }
}
