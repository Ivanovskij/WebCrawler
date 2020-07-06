package org.webcrawler.parser;

import java.util.List;

public class RemoverFacade {

    private List<Remover> removers;

    public RemoverFacade(List<Remover> remover) {
        this.removers = remover;
    }

    public String remove(final String text) {
        String processedText = text;
        for (Remover r: removers) {
            processedText = r.remove(processedText);
        }
        return processedText;
    }
}
