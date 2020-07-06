package org.webcrawler.parser;

/**
 * General interface for removers
 * Removers help to clean string from different useless stuffs
 */
public interface Remover {

    /**
     * Cleans input string
     * @param input - input string
     * @return the same string if user did not override this method
     */
    default String remove(String input) {
        return input;
    }

}
