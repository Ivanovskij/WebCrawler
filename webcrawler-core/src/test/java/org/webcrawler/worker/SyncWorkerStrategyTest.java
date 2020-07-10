package org.webcrawler.worker;

import org.junit.Before;
import org.junit.Test;
import org.webcrawler.model.CrawlingSeed;
import org.webcrawler.model.Page;
import org.webcrawler.util.StringUtil;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SyncWorkerStrategyTest {

    private WorkerStrategy workerStrategy;
    private HttpClient httpClient;
    private HttpResponse response;

    @Before
    public void setUp() {
        workerStrategy = new SyncWorkerStrategy();
        httpClient = mock(HttpClient.class);
        response = mock(HttpResponse.class);
    }

    @Test
    public void shouldReturnSimilarSizeOfOnePageDetail() throws IOException, InterruptedException {
        when(response.body()).thenReturn(StringUtil.TEST_HTML_STRING);
        doReturn(response).when(httpClient).send(any(), any());
        Map<CrawlingSeed, Page> actual = workerStrategy.run(StringUtil.DEFAULT_SEED, 0,1, httpClient);
        Map<CrawlingSeed, Page> expected = new ConcurrentHashMap<>();
        expected.put(new CrawlingSeed(StringUtil.DEFAULT_SEED, 0),
                        new Page(Collections.singletonList("https://hello.html"), StringUtil.TEST_HTML_STRING));
        verify(httpClient, times(1)).send(any(), any());
        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void shouldReturnOnePageDetail() throws IOException, InterruptedException {
        when(response.body()).thenReturn(StringUtil.TEST_HTML_STRING);
        doReturn(response).when(httpClient).send(any(), any());
        Map<CrawlingSeed, Page> actual = workerStrategy.run(StringUtil.DEFAULT_SEED, 0,1, httpClient);
        verify(httpClient, times(1)).send(any(), any());
        Map<CrawlingSeed, Page> expected = new ConcurrentHashMap<>();
        expected.put(new CrawlingSeed(StringUtil.DEFAULT_SEED, 0),
                new Page(Collections.singletonList("https://hello.html"), StringUtil.TEST_HTML_STRING));
        assertEquals(expected.toString(), actual.toString());
    }
}