package com.htmlfetcher.doc;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.26.
 * Time: 23:48
 */
public class PDFConverterTest {

    @Test(expected = IllegalArgumentException.class)
    public void convertNull() throws Exception {
        // Given we have a working converter instance
        IPDFConverter pdfConverter = new PDFConverter();

        // When we provide a page which is null
        pdfConverter.getPdf(null);

        // Then we get an IllegalArgumentException
    }
}
