package org.webcrawler.parser;

import org.junit.Test;
import org.webcrawler.parser.Remover;

import static org.junit.Assert.assertEquals;

public class RemoverTest {

    private static final String OWN_VALUE_TEXT = "own value";

    @Test
    public void shouldReturnOwnValue() {
        DefaultRemover defaultRemover = new DefaultRemover();
        String actual = defaultRemover.remove(OWN_VALUE_TEXT);
        assertEquals(OWN_VALUE_TEXT, actual);
    }
    
    private static class DefaultRemover implements Remover {
    }

}