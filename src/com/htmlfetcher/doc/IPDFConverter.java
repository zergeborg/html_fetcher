package com.htmlfetcher.doc;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.domain.PDF;
import com.htmlfetcher.error.ConverterException;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 23:45
 */
public interface IPDFConverter {
    public PDF getPdf(HtmlPage htmlPage) throws ConverterException;
}
