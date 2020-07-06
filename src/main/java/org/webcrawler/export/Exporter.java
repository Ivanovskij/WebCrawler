package org.webcrawler.export;

import org.webcrawler.model.statistic.Statistic;

import java.util.List;

public interface Exporter {

    boolean exportAllInOne(List<Statistic> data);
    boolean exportSeparately(List<Statistic> data, int numberOfPages);

}
