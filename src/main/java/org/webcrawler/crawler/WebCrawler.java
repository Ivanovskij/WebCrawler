package org.webcrawler.crawler;

import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.worker.WorkerStrategy;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;


public class WebCrawler implements Crawler {

    private final WorkerStrategy workerStrategy;
    private static final int CONNECTION_TIMEOUT_UNIT = 20;

    private static final HttpClient client = HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_UNIT))
            .build();

    public WebCrawler(WorkerStrategy strategy) {
        this.workerStrategy = strategy;
    }

    @Override
    public List<Statistic> crawl(String rootSeed, int depth, CrawlSearcher crawlSearcher) {
        Map<CrawlingSeed, Page> crawledDetails = workerStrategy.run(rootSeed, depth, client);
        return crawlSearcher.search(crawledDetails);
    }

    //todo: сделать какую то обертку аля пустой crawl searcher
    @Override
    public List<Statistic> crawl(String rootSeed, int depth) {
        return crawl(rootSeed, depth, null);
    }

}
