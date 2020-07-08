package org.webcrawler.parser;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.util.StringUtil;

import static org.junit.Assert.assertEquals;

public class HtmlRemoverTest {

    private Remover remover;

    @Before
    public void setUp() {
        remover = new HtmlRemover();
    }

    @Test
    public void shouldRemoveAllHtmlTags() {
        String actual = StringUtil.splitByOneWhitespace(remover.remove(StringUtil.TEST_HTML_STRING));
        String expected = StringUtil.splitByOneWhitespace("Page Title My First Heading My first paragraph. minsk");
        assertEquals(expected, actual);
    }

}