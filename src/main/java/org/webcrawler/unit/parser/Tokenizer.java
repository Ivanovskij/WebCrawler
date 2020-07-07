package org.webcrawler.unit.parser;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Tokenizer {

    private static final String SPACE_DELIMITER = " ";

    /**
     * Method tokenizes input string into tokens with help of space delimiter
     *
     * @param input - not null string for tokenizing
     * @return tokens obtained from the string
     */
    public List<String> tokenize(String input) {
        return Collections.list(new StringTokenizer(input, SPACE_DELIMITER)).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }
}
