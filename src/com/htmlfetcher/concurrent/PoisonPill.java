package com.htmlfetcher.concurrent;

import com.htmlfetcher.domain.DownloadInfo;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.29.
 * Time: 0:51
 */
public class PoisonPill extends DownloadInfo {

    public PoisonPill() {
        super();
    }

    public PoisonPill(URL htmlSource) {
        super(htmlSource);
    }
}
