package com.htmlfetcher.concurrent;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.doc.Event;
import com.htmlfetcher.doc.IListener;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.IParsingStrategy;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 20:43
 */
public class DocProducer extends DocWorker implements Runnable {

    private final BlockingQueue<DownloadInfo> queue;
    private final WebClient webClient;
    private final DownloadInfo task;
    private final IListener listener;
    private final IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy;

    public DocProducer(BlockingQueue<DownloadInfo> queue,
                       WebClient webClient,
                       DownloadInfo task,
                       IListener listener,
                       IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy) {
        this.queue = queue;
        this.webClient = webClient;
        this.task = task;
        this.listener = listener;
        this.parsingStrategy = parsingStrategy;
    }

    @Override
    public void run() {
        try {
            HtmlPage htmlPage = offer();
            schedule(htmlPage);
        } catch (Exception ex) {
            listener.onError(new Event<Exception>(ex));
            Thread.currentThread().interrupt();
        }
    }

    private HtmlPage offer() throws Exception {
        HtmlPage htmlPage = getTitlePage(task);
        task.setHtmlPage(htmlPage);
        queue.put(task);
        return htmlPage;
    }

    private HtmlPage getTitlePage(DownloadInfo downloadInfo) throws IOException {
        HtmlPage htmlPage = (HtmlPage) webClient.getPage(downloadInfo.getHtmlSource().toString());
        return htmlPage;
    }

    private void schedule(HtmlPage htmlPage) throws Exception {
        List<DownloadInfo> downloadInfos = parsingStrategy.run(htmlPage);
        if(downloadInfos != null) {
            for(DownloadInfo downloadInfo : downloadInfos) {
                //TODO: we should create new producer tasks here recursively
            }
        }
    }

}
