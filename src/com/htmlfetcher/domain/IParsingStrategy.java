package com.htmlfetcher.domain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.29.
 * Time: 1:08
 */
public interface IParsingStrategy<P,T> {
    public List<T> run(P task) throws Exception;
}
