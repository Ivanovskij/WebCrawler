package org.webcrawler.parser.util;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParserUtil {

    public static final int WHOLE_COINCIDENCE = 1;
    private static final String NORMAL_SEED_REGEX = "href=\"([^\"]*)\"";
    private static final String STARTS_WITH_HTTP = "http";
    private final static Pattern normalSeedPattern = Pattern.compile(NORMAL_SEED_REGEX, Pattern.DOTALL);

    public ParserUtil() {
    }

    public static List<String> findSeedsFromBody(String body) {
        return new Scanner(body).findAll(normalSeedPattern)
                .distinct()
                .map(matchResult -> matchResult.group(WHOLE_COINCIDENCE))
                .filter(Objects::nonNull)
                .filter(Predicate.not(String::isEmpty))
                .filter(seed -> seed.startsWith(STARTS_WITH_HTTP))
                .collect(Collectors.toList());
    }

}
