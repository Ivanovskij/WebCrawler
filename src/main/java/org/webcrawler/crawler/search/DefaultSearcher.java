package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DefaultSearcher implements CrawlSearcher {

    @Override
    public List<Statistic> search(Map<CrawlingSeed, Page> details) {
        return Collections.emptyList();
    }
}
