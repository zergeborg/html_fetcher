package com.htmlfetcher.doc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.concurrent.DocConsumer;
import com.htmlfetcher.concurrent.DocProducer;
import com.htmlfetcher.concurrent.DocServiceWatcher;
import com.htmlfetcher.concurrent.PoisonPill;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.IParsingStrategy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 21:05
 */
public class TaskScheduler implements ITaskScheduler<DownloadInfo>,
        IListener<Exception, DownloadInfo> {

    private final int NUMBER_OF_CONSUMERS = 4;
    private final IPDFConverter pdfConverter;
    private final WebClient webClient;
    private final IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy;

    private ThreadPoolExecutor executor;
    private BlockingQueue<Runnable> threadQueue;
    private BlockingQueue<DownloadInfo> taskQueue;
    private DocServiceWatcher watchDog;
    private Exception exception;
    private DownloadInfo downloadInfo;

    public TaskScheduler(IParsingStrategy<HtmlPage,DownloadInfo> parsingStrategy,
                         IPDFConverter pdfConverter,
                         WebClient webClient) {
        if(parsingStrategy == null || pdfConverter == null || webClient == null) {
            throw new IllegalArgumentException("Neither web client nor PDF converter must not be null!");
        }
        this.parsingStrategy = parsingStrategy;
        this.pdfConverter = pdfConverter;
        this.webClient = webClient;
    }

    @Override
    public void init() {
        initExecutor();
        initWatchDog();
    }

    @Override
    public void schedule(DownloadInfo task) {
        taskQueue = new LinkedBlockingQueue<DownloadInfo>();
        DocProducer docProducer = new DocProducer(taskQueue, webClient, task, this, parsingStrategy);
        executor.execute(docProducer);
        for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
            DocConsumer docConsumer = new DocConsumer(taskQueue, pdfConverter, this);
            executor.execute(docConsumer);
        }
    }

    @Override
    public DownloadInfo retrieve() throws Exception {
        while(exception == null && downloadInfo == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        shutDownExecutor();
        throwException();
        return downloadInfo;
    }

    @Override
    public void onError(IEvent<Exception> event) {
        exception = event.retrieve();
    }

    @Override
    public void onReady(IEvent<DownloadInfo> event) {
        downloadInfo = event.retrieve();
    }

    private void initExecutor() {
        threadQueue = new LinkedBlockingQueue<Runnable>();
        executor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS, threadQueue);
    }

    private void initWatchDog() {
        watchDog = new DocServiceWatcher(executor);
        new Thread(watchDog).start();
    }

    private void shutDownExecutor() throws Exception{
        for (int i = 0; i < NUMBER_OF_CONSUMERS; i++) {
            taskQueue.put(new PoisonPill());
        }
        executor.shutdown();
        // Wait for all tasks to finish
        try {
            while (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
                // ...
            }
        } catch (InterruptedException e) {}
    }

    private void throwException() throws Exception {
        if(exception != null) {
            throw exception;
        }
    }

}
