package org.webcrawler.util;

public class StringUtil {

    public static final String SPACE_DELIMITER = " ";
    public static final String REMOVE_ALL_WHITESPACES_REGEX = "\\s+";

    public static final String TEST_HTML_STRING = "<div class=\"dash weeks_dash\">\n" +
            "\t\t\t\t\t\t<span class=\"dash_title\">nights</span>\n" +
            "\t\t\t\t\t\t<div class=\"digit\">0</div>\n" +
            "\t\t\t\t\t\t<div class=\"digit\">0</div>\n" +
            "\t\t\t\t\t</div>";

    public static String splitByOneWhitespace(String text) {
        return text.trim().replaceAll(REMOVE_ALL_WHITESPACES_REGEX, SPACE_DELIMITER);
    }

}
