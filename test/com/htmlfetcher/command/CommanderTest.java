package com.htmlfetcher.command;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.doc.*;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.IParsingStrategy;
import com.htmlfetcher.domain.SkillportParsingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: Fogetti
 * Date: 2013.03.26.
 * Time: 22:05
 */
public class CommanderTest {

    private static URL INVALID_URL;
    private static URL VALID_URL;
    private WebClient webClient = new WebClient();
    private PDFConverter pdfConverter = mock(PDFConverter.class);
    private IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy = new SkillportParsingStrategy();
    private ITaskScheduler<DownloadInfo> taskScheduler = new TaskScheduler(parsingStrategy, pdfConverter, webClient);
    private IDocService docService = new DocService(taskScheduler);

    @Before
    public void setUp() throws MalformedURLException {
        INVALID_URL = new URL("file", "localhost", 21, "vmi");
        VALID_URL = new URL("http://localhost");
    }

    @Test(expected = IllegalArgumentException.class)
    public void docServiceNull() throws Exception {
        // Given we try to create the commander with null
        ICommander commander = new Commander(null);

        // When we call the constructor

        // Then IllegalArgumentException gets thrown
    }

    @Test(expected = MalformedURLException.class)
    public void URLNotFound() throws Exception {
        // Given we have the commander class
        ICommander commander = new Commander(docService);

        // When we provide an invalid URL
        commander.download(INVALID_URL);

        // Then the method throws invalid URL exception

    }

}
