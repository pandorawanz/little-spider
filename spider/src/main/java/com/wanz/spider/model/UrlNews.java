package com.wanz.spider.model;

// 继承了 News 类的 UrlNews
public class UrlNews extends News {
    // 同样用 private 修饰符保护
    private String url;

    // UrlNews 构造方法
    public UrlNews(String url, String title, String content) {
        // 调用父类的构造方法，因此可以访问父类的 private 成员变量
        super(title, content);
        this.url = url;
    }

    // url 读取接口
    public String getUrl() {
        return url;
    }

    // 重写 display()
    @Override
    public void display() {
        super.display();
        System.out.println("|URL| " + this.getUrl());
    }
}
