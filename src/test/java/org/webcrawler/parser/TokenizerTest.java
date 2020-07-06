package org.webcrawler.parser;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {

    private RemoverFacade removerFacade;
    private Tokenizer tokenizer;

    @Before
    public void setUp() {
        removerFacade = new RemoverFacade(Arrays.asList(new HtmlRemover(), new SignRemover()));
        tokenizer = new Tokenizer();
    }

    @Test
    public void shouldTokenizeTreeWords() {
        List<String> expected = Arrays.asList("nights","0","0");
        String removedFromSignAndHtml = removerFacade.remove(StringUtil.TEST_HTML_STRING);
        List<String> actual = tokenizer.tokenize(removedFromSignAndHtml);
        assertEquals(expected, actual);
    }

}