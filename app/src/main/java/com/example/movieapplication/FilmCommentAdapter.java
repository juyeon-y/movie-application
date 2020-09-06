package com.example.movieapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class FilmCommentAdapter extends BaseAdapter {
    Context context;
    ArrayList<FilmCommentItem> items = new ArrayList<>();

    public FilmCommentAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(FilmCommentItem item){
        items.add(item);
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        FilmCommentItemView view = null;
        if(convertView == null){
            view = new FilmCommentItemView(context);
        } else {
            view = (FilmCommentItemView) convertView; //이전에 썼던 convertView 재사용
        }

        FilmCommentItem item = items.get(i);
        view.setUserId(item.getId());
        view.setTime(item.getTime());
        view.setComment(item.getComment());
        view.setProfileImage(item.getProfile());
        view.setStars(item.getRating());
        view.setRecommend(item.getRecommendNum());
        view.setReport(item.getReport());



        return view;
    }
}