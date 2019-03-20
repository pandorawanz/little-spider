package com.wanz.spider.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// 继承 NewsReader
public class TextNewsReader extends NewsReader {
    public TextNewsReader(File file) {
        super(file);
    }

    // 实现抽象方法
    @Override
    public News readNews() {
        News news = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String title = reader.readLine(); // 读取title
            reader.readLine(); // 跳过空行
            String content = reader.readLine(); // 读取content
            news = new News(title, content);
        } catch (java.io.IOException e) {
            System.out.println("新闻读取出错");
        }

        return news;
    }
}
