package com.example.android.newsapp;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends ArrayAdapter<News> {

    private final List<News> news;
    private LayoutInflater inflater;

    NewsAdapter(@NonNull Activity context, List<News> news) {
        super(context, 0, news);
        this.news = news;
        inflater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.news_list, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.sectionView.setText(news.get(position).getSectionName());
        holder.titleView.setText(news.get(position).getWebTitle());
        holder.dateView.setText(news.get(position).getWebPublicationDate());
        holder.authorView.setText(news.get(position).getAuthor());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.sectionName_id)
        TextView sectionView;
        @BindView(R.id.webTitle_id)
        TextView titleView;
        @BindView(R.id.pubDate_id)
        TextView dateView;
        @BindView(R.id.authorName_id)
        TextView authorView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
