package com.htmlfetcher.concurrent;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.doc.Event;
import com.htmlfetcher.doc.IListener;
import com.htmlfetcher.doc.IPDFConverter;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.PDF;
import com.htmlfetcher.error.ConverterException;

import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 20:44
 */
public class DocConsumer extends DocWorker implements Runnable {

    private final BlockingQueue<DownloadInfo> queue;
    private final IPDFConverter pdfConverter;
    private final IListener listener;

    public DocConsumer(BlockingQueue<DownloadInfo> queue,
                       IPDFConverter pdfConverter,
                       IListener listener) {
        this.queue = queue;
        this.pdfConverter = pdfConverter;
        this.listener = listener;
    }

    @Override
    public void run() {
        try {
            while (true) {
                DownloadInfo downloadInfo = queue.take();
                if(downloadInfo instanceof PoisonPill) {
                    break;
                }
                consume(downloadInfo);
            }
        } catch (ConverterException ex) {
            listener.onError(new Event<Exception>(ex));
            Thread.currentThread().interrupt();
        } catch (RuntimeException ex) {
            listener.onError(new Event<Exception>(ex));
            Thread.currentThread().interrupt();
        } catch (InterruptedException ex) {
            listener.onError(new Event<Exception>(ex));
            Thread.currentThread().interrupt();
        }
    }

    private void consume(DownloadInfo take) throws ConverterException {
        // Turn the HtmlPage into PDF with flying saucer
        PDF pdf = convert(take.getHtmlPage());
        // Send the result for merging to the listener
        notify(take, pdf);
    }

    private PDF convert(HtmlPage htmlPage) throws ConverterException {
        PDF pdf = pdfConverter.getPdf(null);
        return pdf;
    }

    private void notify(DownloadInfo take, PDF pdf) {
        take.setPdf(pdf);
        listener.onReady(new Event<DownloadInfo>(take));
    }
}
