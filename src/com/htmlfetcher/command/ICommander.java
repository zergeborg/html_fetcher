package com.htmlfetcher.command;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Fogetti
 * Date: 2013.03.26.
 * Time: 22:03
 */
public interface ICommander {
    public void download(URL url) throws Exception;
}
