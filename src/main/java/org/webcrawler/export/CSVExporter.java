package org.webcrawler.export;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.webcrawler.model.statistic.Statistic;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

public class CSVExporter implements Exporter {

    private static final Logger logger = Logger.getLogger(CSVExporter.class.getName());
    public static final String ALL_IN_ONE_FILE_NAME = "AllInOneStatistics";
    public static final String CSV_FORMAT = ".csv";
    private static final String DASH = "-";
    public static final String SEPARATELY_FILE_NAME = "SeparatelyStatistic";
    private static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy-HH-mm-ss";
    private Calendar calendar = Calendar.getInstance();

    @Override
    public boolean exportAllInOne(List<Statistic> data) {
        try (Writer writer = Files.newBufferedWriter(
                Paths.get(ALL_IN_ONE_FILE_NAME + DASH + getFormattedCurrentDate() + CSV_FORMAT)))
        {
            StatefulBeanToCsv<Statistic> csvWriter = getDefaultStatefulBeanToCsv(writer);
            csvWriter.write(data);
            return true;
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
            return false;
        }
    }

    private String getFormattedCurrentDate() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(calendar.getTime());
    }

    private StatefulBeanToCsv<Statistic> getDefaultStatefulBeanToCsv(Writer writer) {
        return new StatefulBeanToCsvBuilder<Statistic>(writer)
                .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                .withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
                .withEscapechar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(false)
                .build();
    }

    @Override
    public boolean exportSeparately(List<Statistic> data, int numberOfPages) {
        if (numberOfPages < 0) {
            throw new IllegalArgumentException("Number of pages should be more than zero.");
        }
        numberOfPages = Math.min(numberOfPages, data.size());
        for (int page = 0; page < numberOfPages; page++) {
            try (Writer writer = Files.newBufferedWriter(
                    Paths.get(SEPARATELY_FILE_NAME + page + DASH + getFormattedCurrentDate() + CSV_FORMAT)))
            {
                StatefulBeanToCsv<Statistic> csvWriter = getDefaultStatefulBeanToCsv(writer);
                csvWriter.write(data.get(page));
            } catch (Exception ex) {
                logger.severe(ex.getMessage());
                return false;
            }
        }
        return true;
    }
}
