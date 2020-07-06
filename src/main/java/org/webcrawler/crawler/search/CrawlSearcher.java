package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;

import java.util.List;
import java.util.Map;

public interface CrawlSearcher {

    List<Statistic> search(Map<CrawlingSeed, Page> details);

}