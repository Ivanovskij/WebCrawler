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

/**
 * Searches terms and them total hints
 */
public class TermHintsSearcher implements CrawlSearcher {

    public static final long NOT_FOUND_TERM = 0L;
    public static final String SORT_DIRECTION_IS_NOT_SUPPORTED = "This sort direction is not supported";
    public static final int DISPLAY_ALL_STATISTICS = 0;
    private final Tokenizer tokenizer;
    private final List<String> terms;
    private final List<TermStatistic> statistics;
    private final RemoverFacade removerFacade;

    /**
     * Initialize class fields and sets the search terms
     * @param terms - specified terms for searching
     */
    public TermHintsSearcher(List<String> terms) {
        this.terms = terms;
        tokenizer = new Tokenizer();
        statistics = new ArrayList<>();
        removerFacade = new RemoverFacade(Arrays.asList(new HtmlRemover(), new SignRemover()));
    }

    /**
     * Method bypasses the crawled details and:
     * 1. calculates total hints
     * 2. forms new more convenient term statistic object and adds to statistics
     * @param details - crawled details
     * @return crawlsearcher interface for the next processing information if needed
     */
    @Override
    public CrawlSearcher search(final Map<CrawlingSeed, Page> details) {
        details.forEach((seed, page) -> {
            Map<String, Long> termsTotalHints = getTotalHints(removerFacade.remove(page.getBody()));
            statistics.add(new TermStatistic(seed.getSeed(), termsTotalHints));
        });
        return this;
    }

    /**
     * Method defines term hints comparator
     * and comparing term hints forming total hints related by sort direction
     * @param sortDirection - sort direction e.g. asc, desc, etc...
     * @return crawlsearcher interface for the next processing information if needed
     */
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

    /**
     * Method limits returning statistics:
     * limit < 0 - throw illegal argument exception
     * limit == 0 - returns all statistics
     * limit > 0 - returns limited statistics
     *
     * @param limit - limit statistics
     * @return limited statistics
     */
    @Override
    public List<Statistic> limit(long limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit should be more than zero");
        } else if (limit == DISPLAY_ALL_STATISTICS) {
            return new ArrayList<>(statistics);
        } else {
            return statistics.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Method tokenizes body into tokens and finds specified terms,
     * if the specified terms were not found
     * method finds not found terms and put them into resulting map
     * @param body - page body from the crawled seed
     * @return map with terms total hints
     */
    private Map<String, Long> getTotalHints(String body) {
        List<String> tokens = tokenizer.tokenize(body);
        Map<String, Long> totalHints = findTerms(terms, tokens).parallelStream()
                        .collect(groupingBy(Function.identity(), counting()));
        totalHints.putAll(getNotFoundTerms(terms, tokens));
        return totalHints;
    }

    /**
     * Method searches not found terms in the provided tokens
     * @param terms - specified terms for searching
     * @param tokens - given words from the page body
     * @return not found terms in the provided tokens
     */
    private Map<String, Long> getNotFoundTerms(List<String> terms, List<String> tokens) {
        return terms.stream()
                .filter(Predicate.not(tokens::contains))
                .collect(toMap(Function.identity(), value-> NOT_FOUND_TERM));
    }

    /**
     * Method searches terms in the provided tokens
     * @param terms - specified terms for searching
     * @param tokens - given words from the page body
     * @return found terms in the provided tokens
     */
    private List<String> findTerms(List<String> terms, List<String> tokens) {
        return tokens.parallelStream()
                .filter(terms::contains)
                .collect(Collectors.toList());
    }
}
