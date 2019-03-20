package com.wanz;

import com.wanz.spider.model.NewsWithRelated;
import com.wanz.spider.model.UrlNewsReader;
import com.wanz.spider.model.Viewable;
import com.wanz.spider.view.ListViewer;

import java.util.*;

public class Main {

    // 本地存储新闻内容，如何在终端显示
    // 1. 抽象出 对象
    // 2. 设计 对象应该具有的属性，状态和行为
    // 3. 思考 对象之间交互
    // 4. 开始写代码

    // 读取的网页数目
    static final int maximumURL = 100;

    public static void main(String[] args) throws Exception {
        long stateTime = System.currentTimeMillis();

        // 广度优先搜索
        Queue<NewsWithRelated> newsQueue = new LinkedList<NewsWithRelated>();

        // 起始Url
        String startUrl = "https://readhub.me/topic/7JC8CoryEdG";

        // 解析页面中的所有内容，并储存在 startNews 对象中
        NewsWithRelated startNews = UrlNewsReader.read(startUrl);

        // count用于计数
        int count = 0;
        Set<String> visited = new HashSet<String>();
        visited.add(startUrl);
        newsQueue.add(startNews);   // 队列储存 NewsWithRelated 对象，用于提供后续搜索顺序
        ArrayList<Viewable> results = new ArrayList<Viewable>();    // 创建 results 数组用于储存结果
        while (!newsQueue.isEmpty() && count <= maximumURL) {
            NewsWithRelated current = newsQueue.poll();     // 取出 NewsWithRelated 对象并开始爬虫
            results.add(current);
            count += 1;
            // 遍历哈希表中的网页相关链接
            for (Map.Entry<String, String> entry : current.getRelateds().entrySet()) {
                String url = entry.getValue();
                NewsWithRelated next = UrlNewsReader.read(url);
                // 避免重复出现已经爬过的url
                if (!visited.contains(url)) {
                    newsQueue.add(next);
                    visited.add(url);
                }
            }
        }

        new ListViewer(results).display();

        long endTime = System.currentTimeMillis();
        System.out.println("总结果数量：" + results.size());
        System.out.println("程序运行时间："+ (endTime - stateTime) + "ms");
    }
}

