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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class TermHintsSearcher implements CrawlSearcher {

    public static final long NOT_FOUND_TERM = 0L;
    private final Tokenizer tokenizer;
    private final List<String> terms;
    private final CrawlSearcherSettings settings;

    public TermHintsSearcher(List<String> terms) {
        this(terms, new CrawlSearcherSettings.Builder().build());
    }

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
            Map<String, Long> termsTotalHints = getTotalHints(settings.setUp(page.getBody()));
            statistics.add(new TermStatistic(seed.getSeed(), termsTotalHints));
        });
        return statistics;
    }

    private Map<String, Long> getTotalHints(String text) {
        List<String> tokens = tokenizer.tokenize(text);
        Map<String, Long> totalHints = findTerms(terms, tokens).parallelStream()
                        .collect(groupingBy(Function.identity(), counting()));
        totalHints.putAll(getNotFoundTerms(terms, tokens));
        return totalHints;
    }

    private Map<String, Long> getNotFoundTerms(List<String> terms, List<String> tokens) {
        return terms.stream()
                .filter(Predicate.not(tokens::contains))
                .collect(Collectors.toMap(Function.identity(), value-> NOT_FOUND_TERM));
    }

    private List<String> findTerms(List<String> terms, List<String> tokens) {
        return tokens.parallelStream()
                .filter(terms::contains)
                .collect(Collectors.toList());
    }
}
