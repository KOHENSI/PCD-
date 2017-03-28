package com.ui4j.sample;

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;

public class ReadHtmlFile {

    public static void main(String[] args) {
        // Load sample file from classpath and get its URL location
        String url = ReadHtmlFile.class.getResource("/sample-page.html").toExternalForm();

        // get the instance of WebKit browser
        BrowserEngine browser = BrowserFactory.getWebKit();

        // navigate to url & show the browser page
        browser.navigate(url).show();
    }
}
