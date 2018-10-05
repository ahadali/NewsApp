package com.example.android.newsapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>{


    NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context,0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }
        News news = getItem(position);
        TextView sectionView = listItemView.findViewById(R.id.sectionName_id);
        TextView titleView = listItemView.findViewById(R.id.webTitle_id);
        TextView dateView = listItemView.findViewById(R.id.pubDate_id);
        TextView authorView = listItemView.findViewById(R.id.authorName_id);

        sectionView.setText(news != null ? news.getSectionName() : null);
        titleView.setText(news != null ? news.getWebTitle() : null);
        dateView.setText(news != null ? news.getWebPublicationDate() : null);
        authorView.setText(news != null ? news.getAuthor() : null);

        return listItemView;
    }
}
