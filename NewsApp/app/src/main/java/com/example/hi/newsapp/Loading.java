package com.example.hi.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;
/**
 * Created by hi on 06-05-2017.
 */
public class Loading extends AsyncTaskLoader<List<News>> {
    private String url;
    public Loading(Context context, String ur1l) {
        super(context);
        url = ur1l;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public List<News> loadInBackground() {
        if (url == null) {
            return null;
        }
        List<News> res = Utils.fetchNews(url);
        return res;
    }
}
