package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;

import java.util.List;
import java.util.Map;

public interface CrawlSearcher {

    CrawlSearcher search(Map<CrawlingSeed, Page> details);
    CrawlSearcher sort(SortDirection direction);
    List<Statistic> limit(long limit);

}