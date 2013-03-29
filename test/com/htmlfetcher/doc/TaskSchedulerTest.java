package com.htmlfetcher.doc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.DummyParsingStrategy;
import com.htmlfetcher.domain.IParsingStrategy;
import com.htmlfetcher.domain.SkillportParsingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 21:08
 */
public class TaskSchedulerTest {

    private static URL INVALID_URL;
    private static URL VALID_URL;
    private static URL EXISTING_URL_LOCATION;
    private WebClient webClient = mock(WebClient.class);
    private PDFConverter pdfConverter = mock(PDFConverter.class);

    @Before
    public void setUp() throws MalformedURLException {
        INVALID_URL = new URL("file", "localhost", 21, "vmi");
        VALID_URL = new URL("http://localhost/");
        EXISTING_URL_LOCATION = new URL("http://www.google.com/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parsingStrategyNull() throws Exception {
        // Given we want to create doc service with null web client
        ITaskScheduler<DownloadInfo> taskScheduler = new TaskScheduler(null, pdfConverter, webClient);

        // When we call the constructor

        // Then IllegalArgumentException gets thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void pdfConverterNull() throws Exception {
        // Given we want to create doc service with null web client
        IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy = new SkillportParsingStrategy();
        ITaskScheduler<DownloadInfo> taskScheduler = new TaskScheduler(parsingStrategy, null, webClient);

        // When we call the constructor

        // Then IllegalArgumentException gets thrown
    }

    @Test(expected = MalformedURLException.class)
    public void downloadInvalidURL() throws Exception {
        // Given we have the doc service
        IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy = new SkillportParsingStrategy();
        ITaskScheduler<DownloadInfo> taskScheduler = new TaskScheduler(parsingStrategy, pdfConverter, webClient);
        when(webClient.getPage(INVALID_URL.toString())).thenThrow(MalformedURLException.class);
        taskScheduler.init();

        // When we call the downloadHTML method with invalid URL
        taskScheduler.schedule(new DownloadInfo(INVALID_URL));
        taskScheduler.retrieve();

        // Then it throws MalformedURLException
    }

    @Test
    public void downloadExistingURLLocation() throws Exception {
        // Given we have the doc service
        WebClient webClient = new WebClient();
        IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy = new DummyParsingStrategy();
        ITaskScheduler<DownloadInfo> taskScheduler = new TaskScheduler(parsingStrategy, pdfConverter, webClient);
        taskScheduler.init();

        // When we call the downloadHTML method with an existing URL
        taskScheduler.schedule(new DownloadInfo(EXISTING_URL_LOCATION));
        DownloadInfo downloadInfo = taskScheduler.retrieve();

        // Then we get a valid DownloadInfo
        assertNotNull(downloadInfo);
    }

}
