package com.wanz.spider.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

//从链接中读取News
public class UrlNewsReader {
    // 返回 NewsWithRelated 类的方法
    public static NewsWithRelated read(String url) throws IOException {
        //Jsoup 读取和解析页面，存入 Document 类成员
        Document doc = Jsoup.connect(url).get();
        // 根据标签来获取相关内容
        Elements titleElements = doc.select("title");
        String title = titleElements.first().text();
        String content = doc.select("meta[property=og:description]").attr("content");

        // select 语法
        // 1. 标签名 "title", "meta", "meta"
        // 2. class ".main___dXbgk"
        // 3. id "#root"
        // 4. 属性 "[name=xxx]"

        /*
        Elements contentElements = doc.select(".summary___1i4y3");
        String content = contentElements.first().text();
        */

        NewsWithRelated news = new NewsWithRelated(url, title, content);

        Elements relatedElements = doc.select(".timeline__item___2luxn");


        for (Element element : relatedElements) {
            String relatedTitle = element.select(".content-item___2j97j").text();
            Elements children  = element.children();    // 子元素
            String relatedUrl = children.get(3).child(0).absUrl("href");

            // .text(): 读取字段
            // get(index): elements中的第几个
            // child(index): element的第几个child

            news.addRelated(relatedTitle, relatedUrl);
        }

        return news;
    }
}
