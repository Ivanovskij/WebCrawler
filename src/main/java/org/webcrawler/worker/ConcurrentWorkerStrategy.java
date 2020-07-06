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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentWorkerStrategy implements WorkerStrategy {

    private static final Logger logger = Logger.getLogger(ConcurrentWorkerStrategy.class.getName());
    private static final int LEVEL_DEEPER = 1;

    private final ConcurrentLinkedQueue<CrawlingSeed> crawlingSeeds;
    private final ConcurrentLinkedQueue<String> seenSeeds;
    private final ConcurrentMap<CrawlingSeed, Page> pageDetails;

    public ConcurrentWorkerStrategy() {
        crawlingSeeds = new ConcurrentLinkedQueue<>();
        seenSeeds = new ConcurrentLinkedQueue<>();
        pageDetails = new ConcurrentHashMap<>();
    }

    @Override
    public Map<CrawlingSeed, Page> run(final String rootSeed, final int depth, final HttpClient client) {
        crawlingSeeds.add(new CrawlingSeed(rootSeed, 0));
        seenSeeds.add(rootSeed);

        CrawlingSeed crawlingSeed;
        HttpRequest request;
        HttpResponse<String> response = null;

        while (!crawlingSeeds.isEmpty()) {
            logger.log(Level.INFO, "queue: {0}", crawlingSeeds);
            crawlingSeed = crawlingSeeds.poll();
            logger.log(Level.INFO, "processing url: {0}", crawlingSeed);

            try {
                request = buildRequest(crawlingSeed, Duration.ofMinutes(1));
                response = sendRequest(client, request);
            } catch (IOException | IllegalArgumentException | InterruptedException e) {
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

    private void addNewSeedsForCrawling(final int crawledDepth, final List<String> linksFromPage) {
        linksFromPage.forEach(seed -> {
            if (!seenSeeds.contains(seed)) {
                crawlingSeeds.add(new CrawlingSeed(seed, crawledDepth));
                seenSeeds.add(seed);
            }
        });
    }

    private HttpRequest buildRequest(CrawlingSeed crawlingSeed, Duration duration) {
        return HttpRequest.newBuilder()
                .uri(URI.create(crawlingSeed.getSeed()))
                .timeout(duration)
                .build();
    }

    private HttpResponse<String> sendRequest(HttpClient client,
                                             HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
