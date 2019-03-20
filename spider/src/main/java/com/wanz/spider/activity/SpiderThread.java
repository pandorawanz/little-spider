package com.wanz.spider.activity;

import com.wanz.spider.model.NewsWithRelated;
import com.wanz.spider.model.SearchSate;
import com.wanz.spider.model.UrlNewsReader;

import java.io.IOException;

public class SpiderThread extends Thread {

    private String url;
    private SearchSate searchSate;

    public SpiderThread(SearchSate searchSate, String url) {
        this.url = url;
        this.searchSate = searchSate;
        start();
    }

    @Override
    public void run() {
        try {
            NewsWithRelated next = UrlNewsReader.read(url);
            searchSate.visit(next);
        } catch (IOException e) {
            System.out.println("Ignored an error page:" + url);
        }
    }
}
