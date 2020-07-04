package org.webcrawler.crawler;

import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.DefaultSearcher;
import org.webcrawler.model.statistic.Statistic;

import java.util.List;

public interface Crawler {
    List<Statistic> crawl(String rootSeed, int depth, CrawlSearcher crawlSearcher);

    default List<Statistic> crawl(String rootSeed, int depth) {
        return crawl(rootSeed, depth, new DefaultSearcher());
    }
}
