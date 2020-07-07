package org.webcrawler;

import org.webcrawler.crawler.Crawler;
import org.webcrawler.crawler.WebCrawler;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.SortDirection;
import org.webcrawler.crawler.search.TermHintsSearcher;
import org.webcrawler.export.CSVExporter;
import org.webcrawler.export.Exporter;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.worker.ConcurrentWorkerStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String rootSeed = args[0];
            int depth = Integer.parseInt(args[1]);
            if (args[2].equals("-st")) {
                Crawler crawler = new WebCrawler(new ConcurrentWorkerStrategy());
                int termStartCounter = 3;
                // arg[3] start terms
                List<String> terms = new ArrayList<>();
                while (!args[termStartCounter].equals("-et")) {
                    terms.add(args[termStartCounter++]);
                }
                // args[termCounter] should be end bracket -et (end term)
                CrawlSearcher crawlSearcher = new TermHintsSearcher(terms);
                List<Statistic> statistics = crawler.crawl(rootSeed, depth, crawlSearcher)
                        .sort(SortDirection.DESC)
                        .limit(0);
                Exporter exporter = new CSVExporter();
                exporter.exportAllInOne(statistics);
                exporter.exportSeparately(statistics, 10);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (Exception ex) {
            ex.getMessage();
            throw new IllegalArgumentException();
        }
    }

}
