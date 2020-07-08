package org.webcrawler.crawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.webcrawler.crawler.search.CrawlSearcher;
import org.webcrawler.crawler.search.DefaultSearcher;
import org.webcrawler.util.StringUtil;
import org.webcrawler.worker.WorkerStrategy;

import java.net.http.HttpClient;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class WebCrawlerTest {

    private CrawlSearcher crawlSearcher;
    @InjectMocks
    private WebCrawler crawler;
    @Mock
    private WorkerStrategy workerStrategy;
    @Mock
    private HttpClient client;

    @Before
    public void setUp() {
        crawlSearcher = new DefaultSearcher();
    }

    @Test
    public void shouldReturnDefaultCrawlerSearcher() {
        CrawlSearcher defaultSearcher = crawler.crawl(StringUtil.DEFAULT_SEED, 0, crawlSearcher);
        assertTrue(defaultSearcher instanceof DefaultSearcher);
    }
}