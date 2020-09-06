package com.example.movieapplication;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import de.hdodenhof.circleimageview.CircleImageView;

public class FilmCommentItemView extends LinearLayout {
    CircleImageView profile_image_view;
    TextView user_id_text;
    TextView time_text;
    RatingBar stars_bar;
    TextView comment_text;
    TextView recommend_text;
    TextView report_text;

    public FilmCommentItemView(Context context) {
        super(context);

        init(context);
    }

    public FilmCommentItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.film_comment_item, this, true);

        profile_image_view = (CircleImageView) findViewById(R.id.profile_image_view);
        user_id_text = (TextView) findViewById(R.id.user_id_text);
        time_text = (TextView) findViewById(R.id.time_text);
        stars_bar = (RatingBar) findViewById(R.id.stars_bar);
        comment_text = (TextView) findViewById(R.id.comment_text);
        recommend_text = (TextView) findViewById(R.id.recommend_text);
        report_text = (TextView) findViewById(R.id.report_text);


    }

    public void setProfileImage(int resId){
        profile_image_view.setImageResource(resId);
    }

    public void setUserId(String user_id){
        user_id_text.setText(user_id);
    }

    public void setTime(String time){
        time_text.setText(time);
    }

    public void setStars(float ratingNum){
        stars_bar.setRating(ratingNum/2);
    }

    public void setComment(String comment){
        comment_text.setText(comment);
    }

    public void setRecommend(String recommendNum){
        recommend_text.setText(recommendNum);
    }

    public void setReport(String report){
        report_text.setText(report);
    }
}
