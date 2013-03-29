package com.htmlfetcher.doc;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.htmlfetcher.domain.PDF;
import com.htmlfetcher.error.ConverterException;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 23:36
 */
public class PDFConverter implements IPDFConverter {

    @Override
    public PDF getPdf(HtmlPage htmlPage) throws ConverterException {
        if(htmlPage == null) {
            throw new IllegalArgumentException("Html page must not be null!");
        }
        return null;
    }
}
