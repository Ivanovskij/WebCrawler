package org.webcrawler.parser;

public class HtmlRemover implements Remover {

    private static final String HTML_TAGS_REGEX = "<.*?>";

    /**
     * Easter egg
     * Old school method (only for 23+)
     * @param input - string with html tags
     * @return string without html tags
     */
    private String removeHtml(String input) {
        return input.replaceAll(HTML_TAGS_REGEX, " ");
    }


    /**
     * Simple method that removes html tags from an input string
     * @param input - raw string with html tags
     * @return string without html tags
     */
    @Override
    public String remove(String input) {
        return removeHtml(input);
    }
}
