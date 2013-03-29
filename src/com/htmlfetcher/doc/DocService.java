package com.htmlfetcher.doc;

import com.htmlfetcher.command.IReadyListener;
import com.htmlfetcher.domain.DownloadInfo;
import com.htmlfetcher.domain.PDF;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 23:00
 */
public class DocService implements IDocService<PDF> {

    private final ITaskScheduler<DownloadInfo> taskScheduler;
    private List<IReadyListener<PDF>> listeners = new ArrayList<>();

    public DocService(ITaskScheduler<DownloadInfo> taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void init() {
        taskScheduler.init();
    }

    @Override
    public void downloadHTML(URL url) throws Exception {
        DownloadInfo downloadInfo = createTask(url);
        PDF pdf = retrievePdf(downloadInfo);
        notifyListeners(pdf);
    }

    @Override
    public void register(IReadyListener<PDF> listener) {
        if(listener == null) {
            throw new IllegalArgumentException("Listener might not be null!");
        }
        listeners.add(listener);
    }

    private DownloadInfo createTask(URL url) {
        DownloadInfo downloadInfo = new DownloadInfo(url);
        return downloadInfo;
    }

    private PDF retrievePdf(DownloadInfo downloadInfo) throws Exception {
        taskScheduler.schedule(downloadInfo);
        downloadInfo = taskScheduler.retrieve();
        return downloadInfo.getPdf();
    }

    private void notifyListeners(PDF pdf) {
        IEvent<PDF> event = new Event<PDF>(pdf);
        for(IReadyListener<PDF> listener : listeners) {
            listener.onReady(event);
        }
    }

}
