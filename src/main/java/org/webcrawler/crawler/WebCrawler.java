package org.webcrawler.crawler;

import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.worker.WorkerStrategy;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Map;


/**
 * Variation of crawler
 * Knows can scan web resources
 */
public class WebCrawler implements Crawler {

    private final WorkerStrategy workerStrategy;
    private static final int CONNECTION_TIMEOUT_UNIT = 20;

    private static final HttpClient client = HttpClient
            .newBuilder()
            .connectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_UNIT))
            .build();

    /**
     * Sets worker strategy
     * @param strategy - worker strategy
     */
    public WebCrawler(WorkerStrategy strategy) {
        this.workerStrategy = strategy;
    }

    /**
     * Method run worker and received crawled details
     * Then calls search method of crawler search
     * with the given search settings for the next processing information
     *
     * @param rootSeed      - start vertex from which we want to start crawl
     * @param depth         - depth of search new vertices
     * @param crawlSearcher - specified settings of crawler
     * @return crawlsearcher interface for the next processing information if needed
     */
    @Override
    public CrawlSearcher crawl(String rootSeed, int depth, CrawlSearcher crawlSearcher) {
        Map<CrawlingSeed, Page> crawledDetails = workerStrategy.run(rootSeed, depth, client);
        return crawlSearcher.search(crawledDetails);
    }

}
