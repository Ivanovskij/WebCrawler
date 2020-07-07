package org.webcrawler.crawler;

import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.DefaultSearcher;

/**
 * General interface for all crawlers
 */
public interface Crawler {

    /**
     * Crawl information based on the given params
     *
     * @param rootSeed - start vertex from which we want to start crawl
     * @param depth    - depth of search new vertices
     * @param crawlSearcher - specified settings of crawler
     * @return crawlsearcher interface for the next processing information if needed
     */
    CrawlSearcher crawl(String rootSeed, int depth, CrawlSearcher crawlSearcher);

    /**
     * This method just only crawl the information without any searches
     *
     * @param rootSeed - start vertex from which we want to start crawl
     * @param depth    - depth of search new vertices
     * @return crawlsearcher interface for the next processing information if needed
     */

    default CrawlSearcher crawl(String rootSeed, int depth) {
        return crawl(rootSeed, depth, new DefaultSearcher());
    }
}
