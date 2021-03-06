package org.webcrawler.worker;

import org.webcrawler.exception.ExceptionUtil;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.parser.util.ParserUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsyncWorkerStrategy implements WorkerStrategy {

    private static final Logger logger = Logger.getLogger(AsyncWorkerStrategy.class.getName());
    private static final int LEVEL_DEEPER = 1;

    private final Queue<CrawlingSeed> crawlingSeeds;
    private final Queue<String> seenSeeds;
    private final Map<CrawlingSeed, Page> pageDetails;

    /**
     * public constructor
     * Needs to initialize class fields
     */
    public AsyncWorkerStrategy() {
        crawlingSeeds = new ConcurrentLinkedDeque<>();
        seenSeeds = new ConcurrentLinkedDeque<>();
        pageDetails = new ConcurrentHashMap<>();
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

        while (!crawlingSeeds.isEmpty() && maxVisitedPages > pageDetails.size()) {
            logger.log(Level.INFO, "queue: {0}", crawlingSeeds);
            crawlingSeed = crawlingSeeds.poll();
            logger.log(Level.INFO, "processing url: {0}", crawlingSeed);
            final CrawlingSeed finalCrawlingSeed = crawlingSeed;

            buildRequest(crawlingSeed, Duration.ofMinutes(1))
                    .thenApply(sendRequest(client))
                    .thenApply(response -> {
                        String responseBody = response != null ? response.body() : "";
                        List<String> linksFromPage = ParserUtil.findSeedsFromBody(responseBody);
                        logger.log(Level.INFO,"for {0} were found {1} urls.", new Object[]{finalCrawlingSeed.getSeed(), linksFromPage.size()});
                        pageDetails.put(finalCrawlingSeed, new Page(linksFromPage, responseBody));
                        return linksFromPage;
                    })
                    .thenAcceptAsync(links -> {
                        if (depth > finalCrawlingSeed.getDepth()) {
                            addNewSeedsForCrawling(finalCrawlingSeed.getDepth() + LEVEL_DEEPER, links);
                        }
                    })
                    .exceptionally(e -> {
                        logger.warning(e.getMessage());
                        return null;
                    })
                    .join();
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
    private CompletableFuture<HttpRequest> buildRequest(final CrawlingSeed crawlingSeed, final Duration duration) {
        return CompletableFuture.supplyAsync(()->HttpRequest.newBuilder()
                .uri(URI.create(crawlingSeed.getSeed()))
                .timeout(duration)
                .build());
    }

    /**
     * Method sends request to the specified constructed request
     * @param client - specified http client
     * @return received response
     */
    public Function<HttpRequest, HttpResponse<String>> sendRequest(HttpClient client) {
        return ExceptionUtil.rethrowFunction(request
                        -> client.send(request, HttpResponse.BodyHandlers.ofString()));
    }

    @Override
    public String toString() {
        return "AsyncWorkerStrategy";
    }
}
