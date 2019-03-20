package com.wanz.spider.model;

import java.util.HashMap;
import java.util.Map;

// 继承UrlNews
public class NewsWithRelated extends UrlNews {
    // 哈希表储存相关链接信息
    private HashMap<String, String> relateds = new HashMap<String,String>();

    // NewsWithRelated 构造方法
    public NewsWithRelated(String url, String title, String content) {
        super(url, title, content);
    }

    // 向哈希表内放入链接 title 和 url 键值对
    public void addRelated(String title, String url) {
        this.relateds.put(title, url);
    }

    // 哈希表访问接口
    public HashMap<String, String> getRelateds() {
        return this.relateds;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("|Related|");
        // 遍历哈希表，Entry 是 Map 的键值对类型，entrySet() 可以从哈希表中取得键值对
        for(Map.Entry<String, String> entry : this.relateds.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}
