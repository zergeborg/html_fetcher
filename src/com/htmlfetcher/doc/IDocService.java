package com.htmlfetcher.doc;

import com.htmlfetcher.command.IReadyListener;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 22:24
 */
public interface IDocService<T> {

    public void init();
    public void downloadHTML(URL url) throws Exception;
    public void register(IReadyListener<T> listener);
}
