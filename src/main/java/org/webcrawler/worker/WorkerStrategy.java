package org.webcrawler.worker;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;

import java.net.http.HttpClient;
import java.util.Map;

public interface WorkerStrategy {

    Map<CrawlingSeed, Page> run(String rootSeed, int depth, HttpClient client);
}
