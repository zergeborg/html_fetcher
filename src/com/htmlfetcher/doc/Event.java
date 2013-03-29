package com.htmlfetcher.doc;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 0:04
 */
public class Event<T> implements IEvent<T> {

    private final T data;

    public Event(T data) {
        this.data = data;
    }

    @Override
    public T retrieve() {
        //TODO: we should consider returning a copy here
        return data;
    }
}
