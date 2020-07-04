package org.webcrawler.parser;

public interface Remover {

    default String remove(String input) {
        return input;
    }

}
