package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;

import java.util.List;
import java.util.Map;

/**
 * General interface of crawl searcher settings
 */
public interface CrawlSearcher {

    /**
     * Searches information from the given crawled details
     * based on the specified crawl searcher
     * @param details - crawled details
     * @return crawlsearcher interface for the next processing information if needed
     */
    CrawlSearcher search(Map<CrawlingSeed, Page> details);

    /**
     * Method allows you to define sorting
     * @param direction - sort direction e.g. asc, desc, etc...
     * @return crawlsearcher interface for the next processing information if needed
     */
    CrawlSearcher sort(SortDirection direction);

    /**
     * Method allows you to limit returning statistic
     * @param limit - limit statistics
     * @return list of statistic by the specified limit
     */
    List<Statistic> limit(long limit);

}