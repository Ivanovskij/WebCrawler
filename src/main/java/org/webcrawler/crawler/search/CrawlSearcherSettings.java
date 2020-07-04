package org.webcrawler.crawler.search;

import org.webcrawler.parser.Remover;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CrawlSearcherSettings {

    private final List<Remover> removers;

    public CrawlSearcherSettings(List<Remover> remover) {
        this.removers = remover;
    }

    public List<Remover> getRemovers() {
        return removers;
    }

    //todo: переделать, @see TermsHintsSearcher->search todo
    public String remove(final String text) {
        String processedText = text;
        for (Remover r: removers) {
            processedText = r.remove(processedText);
        }
        return processedText;
    }

    public static class Builder {
        private List<Remover> removers;

        public Builder() {
        }

        public Builder(List<Remover> remover) {
            this.removers = remover;
        }

        public Builder setRemovers(List<Remover> removers) {
            this.removers = removers;
            return this;
        }

        public CrawlSearcherSettings build() {
            return new CrawlSearcherSettings(
                    Optional.ofNullable(removers).orElse(Collections.emptyList())
            );
        }
    }
}
