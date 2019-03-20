package com.wanz.spider.model;

import java.io.File;

// NewsReader 抽象类
public abstract class NewsReader {
    protected File file;

    // 抽象类也可以有构造方法
    public NewsReader(File file) {
        this.file = file;
    }

    // 抽象方法
    public abstract News readNews();
}
