package org.webcrawler.parser;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.util.StringUtil;

import java.util.ArrayList;
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
    public void shouldTokenizeOnWordsHtmlString() {
        List<String> expected = Arrays.asList("Page", "Title", "My", "First", "Heading", "My", "first", "paragraph", "minsk");
        String removedFromSignAndHtmlBody = removerFacade.remove(StringUtil.TEST_HTML_STRING);
        List<String> actual = tokenizer.tokenize(removedFromSignAndHtmlBody);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmpty_whenGivenEmptyBody() {
        List<String> expected = new ArrayList<>();
        List<String> actual = tokenizer.tokenize("");
        assertEquals(expected, actual);
    }

}