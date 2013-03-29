package com.htmlfetcher.domain;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.29.
 * Time: 1:26
 */
public class DummyParsingStrategy implements IParsingStrategy<HtmlPage,DownloadInfo> {

    @Override
    public List<DownloadInfo> run(HtmlPage task) throws Exception {
        return null;
    }
}
