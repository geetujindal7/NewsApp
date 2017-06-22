package com.example.hi.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hi on 06-05-2017.
 */

public class NewsAdapter extends ArrayAdapter<News>{
    public NewsAdapter(Activity context , List<News> newses)
    {
        super(context,0, newses);
    }
    @NonNull
    @Override
    public View getView(int position , View convertView, ViewGroup parent)
    {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        News getNew = getItem(position);
        TextView textView = (TextView) listView.findViewById(R.id.title1);
        textView.setText(getNew.getTitle());
        TextView newsView = (TextView) listView.findViewById(R.id.news1);
        newsView.setText(getNew.getNews());
        return listView;
    }
}