package org.webcrawler;

import org.webcrawler.crawler.Crawler;
import org.webcrawler.crawler.WebCrawler;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.SortDirection;
import org.webcrawler.crawler.search.TermHintsSearcher;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.worker.AsyncWorkerStrategy;

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
        int depth;

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            List<String> params = lines.collect(Collectors.toList());
            rootSeed = params.get(0);
            depth = Integer.parseInt(params.get(1));
            if (params.get(2).equals("-st")) {
                int termStartCounter = 3;
                // arg[3] start terms
                List<String> terms = new ArrayList<>();
                while (!params.get(termStartCounter).equals("-et")) {
                    terms.add(params.get(termStartCounter));
                    termStartCounter++;
                }
                termStartCounter++; // params[termCounter] should be end bracket -et (end term)

                Crawler crawler = new WebCrawler(new AsyncWorkerStrategy());
                CrawlSearcher crawlSearcher = new TermHintsSearcher(terms);
                List<Statistic> foundStatistics = crawler.crawl(rootSeed, depth, crawlSearcher)
                        .sort(SortDirection.DESC)
                        .limit(0);
                foundStatistics.forEach(System.out::println);
//                boolean isExportToCSV = Optional.of(params.get(termStartCounter).equals("-csv")).orElse(false);
//                if (isExportToCSV) {
//                    Exporter exporter = new CSVExporter();
//                    exporter.exportAllInOne(foundStatistics.limit(0));
//                    exporter.exportSeparately(foundStatistics
//                            .sort(SortDirection.DESC)
//                            .limit(10), 10);
//                }
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
