package com.htmlfetcher.doc;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 21:03
 */
public interface ITaskScheduler<T> {
    public void init();
    public void schedule(T task);
    public T retrieve() throws Exception;
}
