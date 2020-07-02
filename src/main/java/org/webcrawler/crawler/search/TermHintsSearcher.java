package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.model.statistic.TermStatistic;
import org.webcrawler.parser.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class TermHintsSearcher implements CrawlSearcher {

    private final Tokenizer tokenizer;
    private final List<String> terms;
    private final CrawlSearcherSettings settings;

    public TermHintsSearcher(List<String> terms, CrawlSearcherSettings settings) {
        this.terms = terms;
        this.settings = settings;
        tokenizer = new Tokenizer();
    }

    // todo: переделать settings remove:
    // todo: 1. вообще не понятно почему settings удаляет что-то
    // todo: 2. если пользователь еще задаст какие-либо настройки то тут они не выполняться никогда
    // todo: 3. нунжо сделать штуку которая перед поиском выполнит все настройки и будет искать
    @Override
    public List<Statistic> search(Map<CrawlingSeed, Page> details) {
        List<Statistic> statistics = new ArrayList<>();
        details.forEach((seed, page) -> {
            Map<String, Long> termsTotalHints = getTotalHints(settings.remove(page.getText()));
            statistics.add(new TermStatistic(seed.getSeed(), termsTotalHints));
        });
        return statistics;
    }

    private Map<String, Long> getTotalHints(String text) {
        return intersect(terms,
                tokenizer.tokenize(text)).parallelStream()
                        .collect(groupingBy(Function.identity(), counting()));
    }

    private List<String> intersect(List<String> terms, List<String> tokens) {
        return tokens.parallelStream()
                .filter(terms::contains)
                .collect(Collectors.toList());
    }
}
