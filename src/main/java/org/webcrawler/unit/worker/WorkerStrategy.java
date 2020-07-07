package org.webcrawler.unit.worker;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;

import java.net.http.HttpClient;
import java.util.Map;

/**
 * Strategy pattern
 * Needs to provide flexible solution for different types of workers
 * e.g. Concurrent/single worker or sync/async
 */
public interface WorkerStrategy {

    /**
     * Worker looks for specified seeds and
     * collects the information from websites such as links and page details
     *
     * @param rootSeed - start vertex from which we run search
     * @param depth - depth of search for new vertices
     * @param client - specified http client
     * @return information which was found from the seeds
     */
    Map<CrawlingSeed, Page> run(String rootSeed, int depth, HttpClient client);
}
