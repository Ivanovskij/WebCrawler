package org.webcrawler.crawler.search;

import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.model.statistic.Statistic;
import org.webcrawler.model.statistic.TermStatistic;
import org.webcrawler.parser.HtmlRemover;
import org.webcrawler.parser.RemoverFacade;
import org.webcrawler.parser.SignRemover;
import org.webcrawler.parser.Tokenizer;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class TermHintsSearcher implements CrawlSearcher {

    public static final long NOT_FOUND_TERM = 0L;
    public static final String SORT_DIRECTION_IS_NOT_SUPPORTED = "This sort direction is not supported";
    public static final int DISPLAY_ALL_DETAILS = 0;
    private final Tokenizer tokenizer;
    private final List<String> terms;
    private final List<TermStatistic> statistics;
    private RemoverFacade removerFacade;

    public TermHintsSearcher(List<String> terms) {
        this.terms = terms;
        tokenizer = new Tokenizer();
        statistics = new ArrayList<>();
        removerFacade = new RemoverFacade(Arrays.asList(new HtmlRemover(), new SignRemover()));
    }

    @Override
    public CrawlSearcher search(final Map<CrawlingSeed, Page> details) {
        details.forEach((seed, page) -> {
            Map<String, Long> termsTotalHints = getTotalHints(removerFacade.remove(page.getBody()));
            statistics.add(new TermStatistic(seed.getSeed(), termsTotalHints));
        });
        return this;
    }

    @Override
    public CrawlSearcher sort(final SortDirection sortDirection) {
        Comparator<TermStatistic> termHintsComparator = Comparator.comparing(value ->
                        value.getTermsHints()
                                .values()
                                .stream()
                                .mapToLong(i -> i)
                                .sum());

        if (SortDirection.ASC.equals(sortDirection))  {
            statistics.sort(termHintsComparator);
        } else if (SortDirection.DESC.equals(sortDirection)) {
            statistics.sort(termHintsComparator);
            Collections.reverse(statistics);
        } else {
            throw new UnsupportedOperationException(SORT_DIRECTION_IS_NOT_SUPPORTED);
        }

        return this;
    }

    @Override
    public List<Statistic> limit(long limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit should be more than zero");
        } else if (limit == DISPLAY_ALL_DETAILS) {
            return new ArrayList<>(statistics);
        } else {
            return statistics.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }
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
                .collect(toMap(Function.identity(), value-> NOT_FOUND_TERM));
    }

    private List<String> findTerms(List<String> terms, List<String> tokens) {
        return tokens.parallelStream()
                .filter(terms::contains)
                .collect(Collectors.toList());
    }
}
