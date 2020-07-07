package org.webcrawler.parser;

import java.util.List;

/**
 * Facade pattern
 * Facilitates work with removers
 */
public class RemoverFacade {

    private List<Remover> removers;

    /**
     * Setts specified removers
     * @param removers - specified removers
     */
    public RemoverFacade(List<Remover> removers) {
        this.removers = removers;
    }

    /**
     * Runs all specified removers
     * @param text - to be cleaned
     * @return cleaned text
     */
    public String remove(final String text) {
        String processedText = text;
        for (Remover r: removers) {
            processedText = r.remove(processedText);
        }
        return processedText;
    }
}
