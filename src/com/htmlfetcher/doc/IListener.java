package com.htmlfetcher.doc;

import com.htmlfetcher.command.IReadyListener;
import com.htmlfetcher.error.IErrorListener;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.28.
 * Time: 0:42
 */
public interface IListener<T,M> extends IErrorListener<T>, IReadyListener<M> {
}
