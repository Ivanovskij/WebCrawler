package org.webcrawler;

import org.webcrawler.unit.crawler.Crawler;
import org.webcrawler.unit.crawler.WebCrawler;
import org.webcrawler.unit.crawler.search.CrawlSearcher;
import org.webcrawler.unit.crawler.search.SortDirection;
import org.webcrawler.unit.crawler.search.TermHintsSearcher;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.unit.worker.ConcurrentWorkerStrategy;

import java.util.List;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        Crawler crawler = new WebCrawler(new ConcurrentWorkerStrategy());
        CrawlSearcher crawlSearcher = new TermHintsSearcher(
                asList("oleg", "test")
        );
        List<Statistic> allStatistic = crawler.crawl("https://hello.com", 1, crawlSearcher)
                .sort(SortDirection.DESC)
                .limit(10);
    }

}
