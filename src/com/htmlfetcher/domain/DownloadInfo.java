package com.htmlfetcher.domain;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.domain.PDF;

import java.net.URL;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 20:30
 */
public class DownloadInfo {
    private final URL htmlSource;
    private HtmlPage htmlPage;
    private Path pdfPath;
    private PDF pdf;

    protected DownloadInfo() {
        this.htmlSource = null;
    }

    public DownloadInfo(URL htmlSource) {
        this.htmlSource = htmlSource;
    }

    public URL getHtmlSource() {
        return htmlSource;
    }

    public HtmlPage getHtmlPage() {
        return htmlPage;
    }

    public void setHtmlPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public Path getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(Path pdfPath) {
        this.pdfPath = pdfPath;
    }

    public PDF getPdf() {
        return pdf;
    }

    public void setPdf(PDF pdf) {
        this.pdf = pdf;
    }
}
