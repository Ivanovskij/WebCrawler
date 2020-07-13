package org.webcrawler.crawler;

import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.worker.WorkerStrategy;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Variation of crawler
 * Knows can scan web resources
 */
public class WebCrawler implements Crawler {

    private static final Logger logger = Logger.getLogger(WebCrawler.class.getName());

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
    public CrawlSearcher crawl(String rootSeed, int depth, int maxVisitedPages, CrawlSearcher crawlSearcher) {
        logger.log(Level.INFO, "Web crawler is ready and starts to work. " +
                        "Params: rootSeed={0}, depth={1}, maxVisitedPages={2}, worker={3}, crawlerSearcher={4}",
                new Object[]{rootSeed, depth, maxVisitedPages, workerStrategy, crawlSearcher});
        Map<CrawlingSeed, Page> crawledDetails = workerStrategy.run(rootSeed, depth, maxVisitedPages, client);
        return crawlSearcher.search(crawledDetails);
    }

}
