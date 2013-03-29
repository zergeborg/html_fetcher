package com.htmlfetcher.concurrent;

import com.htmlfetcher.domain.DownloadInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.29.
 * Time: 0:48
 */
public abstract class DocWorker {
    private final DownloadInfo POISON_PILL = new PoisonPill();
}
