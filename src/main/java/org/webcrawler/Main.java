package org.webcrawler;

import org.webcrawler.crawler.Crawler;
import org.webcrawler.crawler.WebCrawler;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.CrawlSearcherSettings;
import org.webcrawler.crawler.search.TermHintsSearcher;
import org.webcrawler.model.statistic.TermStatistic;
import org.webcrawler.parser.HtmlRemover;
import org.webcrawler.parser.SignRemover;
import org.webcrawler.worker.ConcurrentWorkerStrategy;

import static java.util.Arrays.asList;

public class Main {

    private static final int DEFAULT_ASYNC_STRATEGY = 0;

    public static void main(String[] args) {
//        String rootSeed = AsyncWorkerStrategy.DEFAULT_ROOT_SEED;
//        int depth = AsyncWorkerStrategy.DEFAULT_DEPTH;
//        int strategy = DEFAULT_ASYNC_STRATEGY;
///*
//        if (args[0] != null) {
//            rootSeed = args[0];
//        } else if (args[1] != null) {
//            depth = Optional.of(Integer.valueOf(args[1])).orElse(AsyncWorkerStrategy.DEFAULT_DEPTH);
//        } else if (args[2] != null) {
//            strategy = Optional.of(Integer.valueOf(args[2])).orElse(DEFAULT_ASYNC_STRATEGY);
//        }*/
//
//        WorkerStrategyEngine worker = new WorkerStrategyEngine(new AsyncWorkerStrategy());
//        if (strategy != 0) {
//            throw new UnsupportedOperationException();
//        }
//        worker.executeStrategy(rootSeed, depth, Arrays.asList("font", "java"));
        Crawler crawler = new WebCrawler(new ConcurrentWorkerStrategy());
        CrawlSearcher crawlSearcher = new TermHintsSearcher(
                asList("telegram", "twitter", "facebook", "likeit", "Minsk", "Russia"),
                new CrawlSearcherSettings.Builder()
                        .setRemovers(asList(new HtmlRemover(), new SignRemover())).build()
        );
        crawler.crawl("https://likeit.by/", 1, crawlSearcher).stream()
            .filter(statistic -> ((TermStatistic)statistic).getTermsHints().size() > 0)
                .forEach(System.out::println);
    }

}
