package org.webcrawler.export;

import org.webcrawler.model.statistic.Statistic;

import java.util.List;

/**
 * General interface of different exports
 */
public interface Exporter {

    /**
     * Export all data in one file
     * @param data - specified data to export
     * @return true - export was successful, false - was not successful
     */
    boolean exportAllInOne(List<Statistic> data);

    /**
     * Export data separately in a new file
     * @param data - specified data to export
     * @param numberOfPages - number of creating pages
     * @return true - export was successful, false - was not successful
     */
    boolean exportSeparately(List<Statistic> data, int numberOfPages);

}
