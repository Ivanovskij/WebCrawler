package org.webcrawler;

import org.webcrawler.crawler.Crawler;
import org.webcrawler.crawler.WebCrawler;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.SortDirection;
import org.webcrawler.crawler.search.TermHintsSearcher;
import org.webcrawler.worker.ConcurrentWorkerStrategy;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        Crawler crawler = new WebCrawler(new ConcurrentWorkerStrategy());
        CrawlSearcher crawlSearcher = new TermHintsSearcher(
                asList("it")
        );
        crawler.crawl("https://likeit.by/", 1, crawlSearcher)
                .sort(SortDirection.DESC)
                .limit(1)
                .forEach(System.out::println);
    }

}
