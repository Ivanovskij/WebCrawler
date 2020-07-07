package org.webcrawler.unit.parser;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.parser.HtmlRemover;
import org.webcrawler.parser.Remover;
import org.webcrawler.parser.RemoverFacade;
import org.webcrawler.parser.SignRemover;
import org.webcrawler.unit.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RemoverFacadeTest {

    private RemoverFacade removerFacade;

    @Before
    public void setUp() {
        List<Remover> removers = Arrays.asList(new HtmlRemover(), new SignRemover());
        removerFacade = new RemoverFacade(removers);
    }

    @Test
    public void shouldReturnWordWithoutHtmlAndSigns() {
        String actual = StringUtil.splitByOneWhitespace(
                removerFacade.remove("%&())&^%$#<p>Hello</p>//..,,"));
        assertEquals("Hello", actual);
    }

    @Test
    public void shouldReturnEmpty_whenGivenEmpty() {
        String actual = removerFacade.remove("");
        assertEquals("", actual);
    }

    @Test(expected = NullPointerException.class)
    public void shouldBeIllegalArgumentException_whenGivenNull() {
        removerFacade.remove(null);
    }
}