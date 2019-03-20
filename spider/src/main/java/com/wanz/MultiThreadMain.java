package com.wanz;

import com.wanz.spider.activity.SpiderThread;
import com.wanz.spider.model.NewsWithRelated;
import com.wanz.spider.model.SearchSate;
import com.wanz.spider.model.UrlNewsReader;
import com.wanz.spider.view.ListViewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiThreadMain {
    public static void main(String[] args) throws Exception {
        long stateTime = System.currentTimeMillis();

        // 广度优先搜索
        SearchSate searchSate = new SearchSate(100);

        String startUrl = "https://readhub.me/topic/7JC8CoryEdG";
        NewsWithRelated startNews = UrlNewsReader.read(startUrl);

        // 起始搜索点放到队列里
        searchSate.visit(startNews);
        while (searchSate.hasTarget()) {
            NewsWithRelated current = searchSate.poll();

            // 把当前页面放入结果，并创建线程表
            searchSate.addResult(current);
            List<SpiderThread> spiders = new ArrayList<SpiderThread>();

            // 处理相关页面
            for (Map.Entry<String, String> entry: current.getRelateds().entrySet()) {
                String url = entry.getValue();
                // 创建线程去把页面爬下来，然后更新queue和visited
                spiders.add(new SpiderThread(searchSate, url));
            }

            // 等待所有线程完成相关页面的读取
            for(SpiderThread spider: spiders) {
                spider.join();
            }
        }

        long endTime = System.currentTimeMillis();

        new ListViewer(searchSate.getResults()).display();
        System.out.println("总结果数量：" + searchSate.getResults().size());
        System.out.println("程序运行时间："+ (endTime - stateTime) + "ms");


    }
}
