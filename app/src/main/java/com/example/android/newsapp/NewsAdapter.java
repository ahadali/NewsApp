package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsItem;
    private Context mContext;

    NewsAdapter(List<News> newsList, Context mContext) {
        this.newsItem = newsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        News currentItem = newsItem.get(position);

        String title = currentItem.getWebTitle();
        String section = currentItem.getSectionName();
        String author = currentItem.getAuthor();
        String pubDate = currentItem.getWebPublicationDate();

        viewHolder.titleView.setText(title);
        viewHolder.sectionView.setText(section);
        viewHolder.authorView.setText(author);
        viewHolder.dateView.setText(pubDate);

    }

    @Override
    public int getItemCount() {
        return newsItem.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final NewsAdapter mAdapter;

        @BindView(R.id.sectionName_id)
        TextView sectionView;
        @BindView(R.id.webTitle_id)
        TextView titleView;
        @BindView(R.id.pubDate_id)
        TextView dateView;
        @BindView(R.id.authorName_id)
        TextView authorView;

        ViewHolder(View view, NewsAdapter adapter) {

            super(view);

            ButterKnife.bind(this, view);

            this.mAdapter = adapter;
        }
    }
}
