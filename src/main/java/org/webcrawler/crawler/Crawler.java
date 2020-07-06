package org.webcrawler.crawler;

import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.DefaultSearcher;

public interface Crawler {
    CrawlSearcher crawl(String rootSeed, int depth, CrawlSearcher crawlSearcher);

    default CrawlSearcher crawl(String rootSeed, int depth) {
        return crawl(rootSeed, depth, new DefaultSearcher());
    }
}
