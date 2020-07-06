package org.webcrawler.parser;

public class SignRemover implements Remover {

    private static final String SIGN_REGEX = "\\W";

    /**
     * Simple method that removes signs from an input string
     * @param input - raw string with signs
     * @return text without html tags
     */
    @Override
    public String remove(String input) {
        return removeSigns(input);
    }


    /**
     * Easter egg
     * Old school method (only for 23+)
     * @param text - raw text with signs
     * @return text without sign
     */
    private String removeSigns(String text) {
        return text.replaceAll(SIGN_REGEX, " ");
    }

}
