package org.webcrawler.worker;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;

import java.net.http.HttpClient;
import java.util.Map;

public class WorkerStrategyEngine {

    private WorkerStrategy workerStrategy;

    public WorkerStrategyEngine() {}

    public void setWorkerStrategy(WorkerStrategy workerStrategy) {
        this.workerStrategy = workerStrategy;
    }

    public Map<CrawlingSeed, Page> executeStrategy(String rootSeed, int depth, HttpClient client) {
        return workerStrategy.run(rootSeed, depth, client);
    }
}
