package com.example.hi.newsapp;

/**
 * Created by hi on 06-05-2017.
 */

public class News {
    private String title;
    private String news;
    private String url;
    public News(String mtitle ,String mnews ,String murl)
    {
        title = mtitle;
        news = mnews;
        url = murl;
    }
    public String getTitle()
    {
        return title;
    }
    public String getNews()
    {
        return news;
    }
    public String getUrl()
    {
        return url;
    }
}