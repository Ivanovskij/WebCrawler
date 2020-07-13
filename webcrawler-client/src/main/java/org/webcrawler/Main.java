package org.webcrawler;

import org.webcrawler.crawler.CrawlSearcherType;
import org.webcrawler.crawler.Crawler;
import org.webcrawler.crawler.WebCrawler;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.DefaultSearcher;
import org.webcrawler.crawler.search.SortDirection;
import org.webcrawler.crawler.search.TermHintsSearcher;
import org.webcrawler.export.CSVExporter;
import org.webcrawler.export.Exporter;
import org.webcrawler.worker.AsyncWorkerStrategy;
import org.webcrawler.worker.SyncWorkerStrategy;
import org.webcrawler.worker.WorkerStrategy;
import org.webcrawler.worker.WorkerType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        String fileName = "crawlsearcher.txt";
        String rootSeed;
        WorkerStrategy workerStrategy = new SyncWorkerStrategy();
        int depth;
        int maxVisitedPages;
        int position;
        CrawlSearcherType crawlSearcherType = CrawlSearcherType.DEFAULT;

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            List<String> params = lines.collect(Collectors.toList());
            // required params start
            rootSeed = params.get(0);
            depth = Integer.parseInt(params.get(1));
            maxVisitedPages = Integer.parseInt(params.get(2));
            // required params end

            position = 3;
            if (isAsyncWorker(position, params)) {
                workerStrategy = new AsyncWorkerStrategy();
                position++;
            }

            // params[position] should be start bracket -st (start term)
            List<String> terms = new ArrayList<>();
            if (isTermsArePresent(position, params, "-st")) {
                crawlSearcherType = CrawlSearcherType.TERMHINTS;
                while (!params.get(++position).equals("-et")) {
                    terms.add(params.get(position));
                }
                position++; // params[position] should be end bracket -et (end term)
            }

            Crawler crawler = new WebCrawler(workerStrategy);
            CrawlSearcher foundStatistics = crawler.crawl(
                    rootSeed,
                    depth,
                    maxVisitedPages,
                    determineCrawlSearcher(crawlSearcherType, terms)
            );

            boolean isExportToCSV = isCSVPresent(position, params, "-csv");
            if (isExportToCSV) {
                Exporter exporter = new CSVExporter();
                exporter.exportAllInOne(foundStatistics.limit(0));
                exporter.exportSeparately(foundStatistics
                        .sort(SortDirection.DESC)
                        .limit(10), 10);
            }

            foundStatistics
                    .sort(SortDirection.DESC)
                    .limit(0)
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isCSVPresent(int position, List<String> params, String csvParam) {
        return isEnd(position, params) && params.get(position).equals(csvParam);
    }

    private static boolean isEnd(int position, List<String> params) {
        return params.size() > position;
    }

    private static boolean isTermsArePresent(int position, List<String> params, String st) {
        return isEnd(position, params) && isCSVPresent(position, params, st);
    }

    private static boolean isAsyncWorker(int position, List<String> params) {
        return isEnd(position, params) && params.get(position).equals(WorkerType.ASYNC.getParam());
    }

    private static CrawlSearcher determineCrawlSearcher(CrawlSearcherType crawlSearcherType, List<String> terms) {
        if (crawlSearcherType.equals(CrawlSearcherType.TERMHINTS)) {
            return new TermHintsSearcher(terms);
        }
        return new DefaultSearcher();
    }

}
