package com.htmlfetcher.doc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.command.Commander;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.IParsingStrategy;
import com.htmlfetcher.domain.PDF;
import com.htmlfetcher.domain.SkillportParsingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 22:56
 */
public class DocServiceTest {

    private static URL INVALID_URL;
    private static URL VALID_URL;
    private static URL EXISTING_URL_LOCATION;
    private WebClient webClient = mock(WebClient.class);
    private PDFConverter pdfConverter = mock(PDFConverter.class);
    private IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy = new SkillportParsingStrategy();
    private ITaskScheduler<DownloadInfo> taskScheduler = new TaskScheduler(parsingStrategy, pdfConverter, webClient);

    @Before
    public void setUp() throws MalformedURLException {
        INVALID_URL = new URL("file", "localhost", 21, "vmi");
        VALID_URL = new URL("http://localhost");
        EXISTING_URL_LOCATION = new URL("http://google.hu");
    }

    @Test(expected = MalformedURLException.class)
    public void downloadInvalidURL() throws Exception {
        // Given we have the doc service
        IDocService docService = new DocService(taskScheduler);
        when(webClient.getPage(INVALID_URL.toString())).thenThrow(MalformedURLException.class);
        docService.init();

        // When we call the downloadHTML method with invalid URL
        docService.downloadHTML(INVALID_URL);

        // Then it throws MalformedURLException
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerNull() throws Exception {
        // Given we have the doc service
        IDocService docService = new DocService(taskScheduler);
        docService.init();

        // When we call the register method with null
        docService.register(null);

        // Then it throws IllegalArgumentException
    }

    @Test
    public void notificationArrives() throws Exception {
        // Given we have the doc service
        DocService docService = new DocService(taskScheduler);
        docService.init();
        Commander commander = mock(Commander.class);

        // When we register a listener
        docService.register(commander);
        // And we call the downloadHTML method with a syntactically correct URL
        docService.downloadHTML(VALID_URL);

        // Then our listener gets notified
        verify(commander).onReady((IEvent<PDF>) anyObject());
    }
}
