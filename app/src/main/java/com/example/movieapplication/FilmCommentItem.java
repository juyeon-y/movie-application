package com.example.movieapplication;

import android.graphics.drawable.Drawable;

public class FilmCommentItem {

    String user_id;
    String time;
    String comment;
    int profileResId;
    float ratingNum;
    String recommendNum;
    String report;


    public FilmCommentItem(String id, String time, String comment, int profileResId, float ratingNum, String recommendNum, String report) {
        this.user_id = id;
        this.time = time;
        this.comment = comment;
        this.profileResId = profileResId;
        this.ratingNum = ratingNum;
        this.recommendNum = recommendNum;
        this.report = report;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProfile() {
        return profileResId;
    }

    public void setProfile(int profileResId) {
        this.profileResId = profileResId;
    }

    public float getRating() {
        return ratingNum;
    }

    public void setRating(float ratingNum) {
        this.ratingNum = ratingNum;
    }

    public String getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(String recommendNum) {
        this.recommendNum = recommendNum;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "FilmCommentItem{" +
                "id='" + user_id + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                ", profilePic=" + profileResId +
                ", rating=" + ratingNum +
                ", recommendNum='" + recommendNum + '\'' +
                ", report='" + report + '\'' +
                '}';
    }
}
