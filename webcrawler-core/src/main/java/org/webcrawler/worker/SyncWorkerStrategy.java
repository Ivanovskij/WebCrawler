package org.webcrawler.worker;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.parser.util.ParserUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concurrent type of worker
 */
public class SyncWorkerStrategy implements WorkerStrategy {

    private static final Logger logger = Logger.getLogger(SyncWorkerStrategy.class.getName());
    private static final int LEVEL_DEEPER = 1;

    private final Queue<CrawlingSeed> crawlingSeeds;
    private final Queue<String> seenSeeds;
    private final Map<CrawlingSeed, Page> pageDetails;

    /**
     * public constructor
     * Needs to initialize class fields
     */
    public SyncWorkerStrategy() {
        crawlingSeeds = new LinkedList<>();
        seenSeeds = new LinkedList<>();
        pageDetails = new HashMap<>();
    }

    /**
     * Algorithm of worker:
     * 1. Add root seed
     * 2. while queue is not empty
     * 3. poll seed
     * 4. make request and get response
     * 5. processed the information
     * 6. if depth is not reached add new seeds for crawling and in seenSeeds queue
     * 7. go to the second point
     *
     * @param rootSeed - start vertex from which we run search
     * @param depth    - depth of search new vertices
     * @param client   - specified http client
     * @return information which was found from the seeds
     */
    @Override
    public Map<CrawlingSeed, Page> run(final String rootSeed,
                                       final int depth,
                                       final int maxVisitedPages,
                                       final HttpClient client) {
        crawlingSeeds.add(new CrawlingSeed(rootSeed, 0));
        seenSeeds.add(rootSeed);

        CrawlingSeed crawlingSeed;
        HttpRequest request;
        HttpResponse<String> response = null;

        while (!crawlingSeeds.isEmpty() && maxVisitedPages > pageDetails.size()) {
            logger.log(Level.INFO, "queue: {0}", crawlingSeeds);
            crawlingSeed = crawlingSeeds.poll();
            logger.log(Level.INFO, "processing url: {0}", crawlingSeed);

            try {
                request = buildRequest(crawlingSeed, Duration.ofMinutes(1));
                response = sendRequest(client, request);
            } catch (IOException | InterruptedException e) {
                logger.warning(e.getMessage());
            }

            String responseBody = response != null ? response.body() : "";
            List<String> linksFromPage = ParserUtil.findSeedsFromBody(responseBody);
            pageDetails.put(crawlingSeed, new Page(linksFromPage, responseBody));

            if (depth > crawlingSeed.getDepth()) {
                addNewSeedsForCrawling(crawlingSeed.getDepth() + LEVEL_DEEPER, linksFromPage);
            }
        }

        logger.log(Level.INFO, "All {0} seeds were processed", pageDetails.size());
        return pageDetails;
    }

    /**
     * Method adds new seeds for crawling in seenSeeds and current crawling seeds queues
     *
     * @param crawledDepth - current vertex depth
     * @param linksFromPage - found links from processed page
     */
    private void addNewSeedsForCrawling(final int crawledDepth, final List<String> linksFromPage) {
        linksFromPage.forEach(seed -> {
            if (!seenSeeds.contains(seed)) {
                crawlingSeeds.add(new CrawlingSeed(seed, crawledDepth));
                seenSeeds.add(seed);
            }
        });
    }

    /**
     * Method build http request
     *
     * @param crawlingSeed - uri to create request
     * @param duration - the timeout duration
     * @return constructed http request
     */
    private HttpRequest buildRequest(CrawlingSeed crawlingSeed, Duration duration) {
        return HttpRequest.newBuilder()
                .uri(URI.create(crawlingSeed.getSeed()))
                .timeout(duration)
                .build();
    }

    /**
     * Method sends request to the specified constructed request
     *
     * @param client - specified http client
     * @param request - constructed request with the given uri
     * @return received response
     * @throws IOException io exceptions
     * @throws InterruptedException interrupted exceptions
     */
    private HttpResponse<String> sendRequest(HttpClient client,
                                             HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
