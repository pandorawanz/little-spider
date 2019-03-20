package com.wanz.spider.model;

import java.util.*;

// 用于储存同步线程之间的状态
public class SearchSate {
    private final int maximumResults;

    // 共享
    private Queue<NewsWithRelated> newsQueue = new LinkedList<NewsWithRelated>();
    private int count = 0;

    // 共享
    private Set<String> visited = new HashSet<String>();
    private ArrayList<Viewable> results = new ArrayList<Viewable>();

    public SearchSate(int maxnumResults) {
        this.maximumResults = maxnumResults;
    }

    public synchronized NewsWithRelated poll() {
        return newsQueue.poll();
    }

    // 只被主线程访问
    public void addResult(NewsWithRelated result) {
        results.add(result);
        count += 1;
    }

    public synchronized void visit(NewsWithRelated news) {
        if (!visited.contains(news.getUrl())) {
            newsQueue.add(news);
            visited.add(news.getUrl());
        }
    }

    public synchronized int getCount() {
        return count;
    }


    public synchronized boolean hasTarget() {
        return !newsQueue.isEmpty() && count < maximumResults;
    }

    // 只被主线程访问
    public  ArrayList<Viewable> getResults() {
        return results;
    }


}
