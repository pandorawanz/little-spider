## Little Spider
本爬虫项目主要用于读取网页新闻队列，目标网页为[https://readhub.cn/topics](https://readhub.cn/topics)

本爬虫代码主要有以下特点：
1.将对象模型逐层封装，便于扩展功能
2.用Jsoup解析网页html，读取相关信息
3.采用了广度优先搜索方式，将网页相关链接，存入队列，并顺沿队列读取信息
4.尝试采用多线程实现了代码功能，将线程之间的共存状态封装成类实例

### 封装
层次结构：Viewable -> News -> UrlNews -> NewsWithRelated

- **Viewable**:抽象出了子类必须定义的功能display()
- **News**:新闻的主体结构
- **UrlNews**:从网页读取的新闻
- **NewsWithRelated**:从网页读取的新闻，附带相关新闻信息

### 读取信息
用Jsoup库解析了网页的html,分为几个步骤：
1.连接至网页地址，解析并生成Document对象：

```
Document doc = Jsoup.connect(url).get();
```

2.根据需求信息的标签，选择出相应的元素：
```
Elements titleElements = doc.select("title");
```

3.读取元素里的具体信息：
```
String title = titleElements.first().text();
String content = doc.select("meta[property=og:description]").attr("content");
```

4.后续遍历元素，将相应信息添加进NewsWithRelated对象：
```
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
```

### 广度优先搜索
将上层网页获取的链接全部读取，用队列存储，再向下层读取，用HashSet记录避免了重复读取
```
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
```

### 多线程实现
1.定义并创建SearchState对象来存储和处理线程之间的状态
2.分层读取，用join()方法控制主线程的运行状态
3.判定是否已经读取过相同信息的方法，用synchronized修饰符确保其只能同时被一个线程调用，防止线程交叉导致的异常出现