package org.webcrawler.parser;

public class SignRemover implements Remover {

    private static final String SIGN_REGEX = "\\W";

    /**
     * Simple method that removes signs from an input string
     * @param input - raw string with signs
     * @return string without signs
     */
    @Override
    public String remove(String input) {
        return removeSigns(input);
    }


    /**
     * Easter egg
     * Old school method (only for 23+)
     * @param input - raw string with signs
     * @return string without signs
     */
    private String removeSigns(String input) {
        return input.replaceAll(SIGN_REGEX, " ");
    }

}
