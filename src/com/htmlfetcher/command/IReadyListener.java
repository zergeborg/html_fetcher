package com.htmlfetcher.command;

import com.htmlfetcher.doc.IEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 22:40
 */
public interface IReadyListener<T> {

    public void onReady(IEvent<T> event);
}
