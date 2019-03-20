package com.wanz.spider.model;

// News 继承了Viewable接口
public class News implements Viewable {
    // private修饰的成员变量不能被子类访问
    private String title;
    private String content;

    // News 构造方法
    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 提供 title 的读取接口
    public String getTitle() {
        return title;
    }

    //提供 content 的读取接口
    public String getContent() {
        return content;
    }

    // 实现 display() 用于展示信息
    public void display() {
        System.out.println("|Title| " + this.getTitle());
        System.out.println("|Content| " + this.getContent());
    }
}
