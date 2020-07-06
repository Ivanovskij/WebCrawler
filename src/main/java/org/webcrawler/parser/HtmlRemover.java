package org.webcrawler.parser;

public class HtmlRemover implements Remover {

    private static final String HTML_TAGS_REGEX = "<.*?>";

    /**
     * Easter egg
     * Old school method (only for 23+)
     * @param text - text with html tags
     * @return text without html tags
     */
    private String removeHtml(String text) {
        return text.replaceAll(HTML_TAGS_REGEX, " ");
    }



    @Override
    public String remove(String input) {
        return removeHtml(input);
    }
}
