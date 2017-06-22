package com.example.hi.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import static com.example.hi.newsapp.R.string.em;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>
{
 private  static String url ="https://content.guardianapis.com/search?page-size=10&q=news&api-key=0fc305c0-c13b-40dd-b619-cc5149f58189";
    private TextView empty1;
    private NewsAdapter adapter;
    private int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView news = (ListView) findViewById(R.id.line1);
        empty1 = (TextView) findViewById(R.id.empty);
        adapter = new NewsAdapter(this, new ArrayList<News>());
        news.setAdapter(adapter);
        news.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
                 @Override
            public void onItemClick(AdapterView<?> adapterView , View view , int position, long l)
                 {
                     News updated = adapter.getItem(position);
                     Uri newws = Uri.parse(updated.getUrl());
                     Intent webnews = new Intent(Intent.ACTION_VIEW, newws);
                     startActivity(webnews);
                 }
        });
        LoaderManager loaderManager = null;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info!=null && info.isConnected())
        {
            empty1.setVisibility(View.GONE);
            loaderManager = getLoaderManager();
            loaderManager.initLoader(id,null,MainActivity.this);
        }
        else
        {
            empty1.setText(getString(R.string.nc));
        }
}
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle args) {
        return new Loading(this,url);
    }
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        adapter.clear();
        empty1.setText(getString(em));
        if(data!=null && !data.isEmpty())
        {
            adapter.addAll(data);
        }
        else
            empty1.setVisibility(View.VISIBLE);
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
    adapter.clear();
    }
}