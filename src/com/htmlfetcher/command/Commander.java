package com.htmlfetcher.command;

import com.htmlfetcher.doc.IDocService;
import com.htmlfetcher.doc.IEvent;
import com.htmlfetcher.domain.PDF;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 22:19
 */
public class Commander implements ICommander, IReadyListener<PDF> {

    private final IDocService docService;

    public Commander(IDocService<PDF> docService) {
        if(docService == null) {
            throw new IllegalArgumentException("Doc service must not be null!");
        }
        this.docService = docService;
        this.docService.register(this);
    }

    @Override
    public void download(URL url) throws Exception {
        docService.downloadHTML(url);
    }

    @Override
    public void onReady(IEvent<PDF> event) {
    }
}
