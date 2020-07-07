package org.webcrawler.unit.parser.util;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Util parser class
 */
public class ParserUtil {

    public static final int WHOLE_COINCIDENCE = 1;
    private static final String NORMAL_SEED_REGEX = "href=\"([^\"]*)\"";
    private static final String STARTS_WITH_HTTP = "http://";
    private static final String STARTS_WITH_HTTPS = "https://";
    private static final Pattern normalSeedPattern = Pattern.compile(NORMAL_SEED_REGEX, Pattern.DOTALL);

    /**
     * Private constructor
     * Util class
     */
    private ParserUtil() {
    }

    /**
     * Method finds seeds from page body
     * Removes duplicates
     * Filtering by non null, not empty and starts with http and https
     *
     * @param body - page body information from crawling seed
     * @return found seeds from the given body
     */
    public static List<String> findSeedsFromBody(String body) {
        try (Scanner scanner = new Scanner(body)) {
            return scanner.findAll(normalSeedPattern)
                    .distinct()
                    .map(matchResult -> matchResult.group(WHOLE_COINCIDENCE))
                    .filter(Objects::nonNull)
                    .filter(Predicate.not(String::isEmpty))
                    .filter(seed -> seed.startsWith(STARTS_WITH_HTTP) || seed.startsWith(STARTS_WITH_HTTPS))
                    .collect(Collectors.toList());
        }
    }
}
