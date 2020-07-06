package org.webcrawler.util;

public class StringUtil {

    public static final String SPACE_DELIMITER = " ";
    public static final String REMOVE_ALL_WHITESPACES_REGEX = "\\s+";

    public static final String TEST_HTML_STRING = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Page Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h1>My First Heading</h1>\n" +
            "<p>My first paragraph.</p>\n" +
            "\n" +
            "<a href=\"https://hello.html\">minsk</a>" +
            "</body>\n" +
            "</html>\n";

    public static String splitByOneWhitespace(String text) {
        return text.trim().replaceAll(REMOVE_ALL_WHITESPACES_REGEX, SPACE_DELIMITER);
    }

}
