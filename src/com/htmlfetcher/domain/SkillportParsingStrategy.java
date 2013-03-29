package com.htmlfetcher.domain;

import com.gargoylesoftware.htmlunit.html.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.29.
 * Time: 1:10
 */
public class SkillportParsingStrategy implements IParsingStrategy<HtmlPage,DownloadInfo> {

    @Override
    public List<DownloadInfo> run(HtmlPage htmlPage) throws Exception {
        // Find id="toc" element
        HtmlTable htmlTable = findTOCElement(htmlPage);
        // Get list of URLs from anchors inside TOC
        DomNodeList<HtmlElement> elementList = getURLs(htmlTable);
        // Create new DownloadInfo from each URL
        return schedule(htmlPage, elementList);
    }

    private HtmlTable findTOCElement(HtmlPage htmlPage) {
        HtmlTable htmlTable = (HtmlTable) htmlPage.getElementById("toc");
        return htmlTable;
    }

    private DomNodeList<HtmlElement> getURLs(HtmlTable htmlTable) {
        DomNodeList<HtmlElement> elementList = htmlTable.getElementsByTagName("a");
        return elementList;
    }

    private List<DownloadInfo> schedule(HtmlPage htmlPage, DomNodeList<HtmlElement> elementList) throws MalformedURLException, InterruptedException {
        List<DownloadInfo> downloadInfos = null;
        for(HtmlElement element : elementList) {
            downloadInfos = new ArrayList<>();
            String urlStart = htmlPage.getUrl().toString();
            HtmlAnchor anchor = (HtmlAnchor)element;
            String urlEnd = anchor.getHrefAttribute();
            downloadInfos.add(new DownloadInfo(new URL(urlStart + "/" + urlEnd)));
        }
        return downloadInfos;
    }
}
