package org.webcrawler.unit.parser;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.unit.util.StringUtil;

import static org.junit.Assert.assertEquals;

public class SignRemoverTest {

    private Remover remover;

    @Before
    public void setUp() {
        remover = new SignRemover();
    }

    @Test
    public void shouldRemoveAllSigns() {
        String actual = StringUtil.splitByOneWhitespace(remover.remove(StringUtil.TEST_HTML_STRING));
        String expected = StringUtil.splitByOneWhitespace("DOCTYPE html html head title Page Title title head body  h1 My First Heading h1 p My first paragraph p  a href https hello html minsk a body html");
        assertEquals(expected, actual);
    }

}