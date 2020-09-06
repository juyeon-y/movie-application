package com.example.movieapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.movieapplication.data.CommentInfo;
import com.example.movieapplication.data.CommentList;
import com.example.movieapplication.data.MovieInfo;
import com.example.movieapplication.data.MovieList;
import com.example.movieapplication.data.ResponseInfo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MovieDetailFragment extends Fragment {
    boolean upClicked = false;
    boolean downClicked = false;
    int upNum;
    int downNum;
    int id;
    //private Context context;

    ImageView poster;
    ImageView gradeImage;
    TextView filmDescription;
    TextView filmName;
    TextView thumbsUp;
    TextView thumbsDown;
    TextView reservationRate;
    TextView audience;
    TextView filmSynopsisContent;
    TextView filmCastContent;
    TextView ratingNum;
    ListView listView;

    RatingBar total_stars_bar; //뷰 중에 속성을 바꿀 것만 onCreate 클래스 밖에다가 선언

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie_detail, container, false);

        //context = container.getContext();

        poster = (ImageView)rootView.findViewById(R.id.poster);
        filmName = (TextView)rootView.findViewById(R.id.filmName);
        gradeImage = (ImageView)rootView.findViewById(R.id.gradeImage);
        filmDescription = (TextView)rootView.findViewById(R.id.filmDescription);
        thumbsUp = (TextView)rootView.findViewById(R.id.thumbsUp);
        thumbsDown = (TextView)rootView.findViewById(R.id.thumbsDown);
        reservationRate = (TextView)rootView.findViewById(R.id.reservationRate);
        total_stars_bar = (RatingBar)rootView.findViewById(R.id.total_stars_bar);
        audience = (TextView)rootView.findViewById(R.id.audience);
        filmSynopsisContent = (TextView)rootView.findViewById(R.id.filmSynopsisContent);
        filmCastContent = (TextView)rootView.findViewById(R.id.filmCastContent);
        ratingNum = (TextView)rootView.findViewById(R.id.ratingNum);
        listView = (ListView)rootView.findViewById(R.id.listView);

        id = getArguments().getInt("id");

        requestMovie(id);

        thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upClicked == false){
                    if(downClicked == true){
                        requestDislike(id, false);
                    }
                    requestLike(id, true);
                }
                else{
                    requestLike(id, false);
                }
            }
        });

        thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downClicked == false) {
                    if (upClicked == true) {
                        requestLike(id, false);
                    }
                    requestDislike(id, true);
                } else {
                    requestDislike(id, false);
                }
            }
        });

        TextView writeComment = (TextView)rootView.findViewById(R.id.writeComment);
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentWriteActivity();
            }
        });

        Button seeMoreBtn = (Button)rootView.findViewById(R.id.seeMoreBtn);
        seeMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "모두보기 버튼 눌림",Toast.LENGTH_LONG).show();
                showSeeMoreActivity();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        request2CommentList(id);
    }

    public void showCommentWriteActivity(){
        Intent intent = new Intent(getContext(), CommentWriteActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        //startActivityForResult(intent, 101);
    }

    public void showSeeMoreActivity(){
        Intent intent = new Intent(getContext(), SeeMoreActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        /*
        ArrayList<CommentData> commentList = new ArrayList<CommentData>();

        FilmCommentItem item;
        for (int i = 0; i < adapter.getCount(); i++) {
            item = (FilmCommentItem) adapter.getItem(i);
            CommentData data = new CommentData(item.comment, item.ratingNum);
            commentList.add(data);
        }
        intent.putParcelableArrayListExtra("comments", commentList);
        startActivityForResult(intent, 102);
         */
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 101){
            if(intent != null){
                String contents = intent.getStringExtra("contents");
                float rating = intent.getFloatExtra("rating", 0.0f);
                adapter.addItem(new FilmCommentItem("ㅁㅁ**", "11분전", contents, R.drawable.user1, rating, "추천 2", "신고하기"));
                adapter.notifyDataSetChanged();
            }
        }

        else if(requestCode == 102 ){
            if(intent != null){
                ArrayList<CommentData> commentList = intent.getParcelableArrayListExtra("newComments");
                if(commentList != null){
                    for(int i=0; i<commentList.size(); i++){
                        adapter.addItem(new FilmCommentItem("lym81**", "12분전", commentList.get(i).comment, R.drawable.user1, commentList.get(i).ratingNum, "추천 2", "신고하기"));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
     */

    public void requestMovie(int id){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        url += "?" + "id=" + id;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, "응답 받음.", Toast.LENGTH_SHORT).show();
                        processMovieResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "에러 발생." + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        //Toast.makeText(context, "영화목록 요청 보냄.", Toast.LENGTH_SHORT).show();

    }

    public void processMovieResponse(String response){
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200) {
            MovieList movieList = gson.fromJson(response, MovieList.class);
            MovieInfo movieInfo = movieList.result.get(0);

            Glide.with(this).load(movieInfo.thumb).into(poster);
            filmName.setText(movieInfo.title);
            switch (movieInfo.grade){
                case 12:
                    gradeImage.setImageResource(R.drawable.ic_12);
                    break;
                case 15:
                    gradeImage.setImageResource(R.drawable.ic_15);
                    break;
                case 19:
                    gradeImage.setImageResource(R.drawable.ic_19);
                    break;
            }
            String[] array = movieInfo.date.split("-");
            filmDescription.setText(array[0]+"."+array[1]+"."+array[2]+" 개봉\n"+movieInfo.genre+" / "+movieInfo.duration+" 분");
            upNum = movieInfo.like;
            downNum = movieInfo.dislike;
            thumbsUp.setText(Integer.toString(movieInfo.like));
            thumbsDown.setText(Integer.toString(movieInfo.dislike));
            reservationRate.setText(movieInfo.reservation_grade+"위 "+movieInfo.reservation_rate+"%");
            total_stars_bar.setRating(movieInfo.user_rating);
            ratingNum.setText(Float.toString(movieInfo.user_rating * 2));
            audience.setText(Integer.toString(movieInfo.audience));
            filmSynopsisContent.setText(movieInfo.synopsis);
            filmCastContent.setText("감독 "+movieInfo.director+"\n출연 "+movieInfo.actor);
        }
    }

    public void request2CommentList(int id){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + id + "&limit=2";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, "응답 받음.", Toast.LENGTH_SHORT).show();
                        processCommentResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "에러 발생." + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        //Toast.makeText(context, "영화목록 요청 보냄.", Toast.LENGTH_SHORT).show();

    }

    public void processCommentResponse(String response){
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200) {
            CommentList commentList = gson.fromJson(response, CommentList.class);
            FilmCommentAdapter adapter = new FilmCommentAdapter(getContext());

            for(int i=0; i<commentList.result.size(); i++){
                CommentInfo commentInfo = commentList.result.get(i);

                String[] array = commentInfo.time.split(" ");
                String[] parseDate = array[0].split("-");
                String date = parseDate[0] + "." + parseDate[1] + "." + parseDate[2];

                adapter.addItem(new FilmCommentItem(commentInfo.writer, date, commentInfo.contents, R.drawable.user1, commentInfo.rating, "추천 " + commentInfo.recommend, "신고하기"));
                //String id, String time, String comment, int profileResId, float ratingNum, String recommendNum, String report
            }
            //Toast.makeText(context, "영화 갯수 : " + movieList.result.size(), Toast.LENGTH_SHORT).show();
            listView.setAdapter(adapter);
        }
    }

    public void requestLike(int id, boolean likeyn){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/increaseLikeDisLike";
        url += "?" + "id=" + id;

        final boolean like;

        if(likeyn){
            url += "&likeyn=Y";
            like = true;
        }
        else{
            url += "&likeyn=N";
            like = false;
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, "응답 받음.", Toast.LENGTH_SHORT).show();
                        processLikeResponse(response, like);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, "에러 발생." + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        //Toast.makeText(context, "영화목록 요청 보냄.", Toast.LENGTH_SHORT).show();

    }

    public void processLikeResponse(String response, boolean like){
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200) {
            if(like){
                upNum = upNum + 1;
                thumbsUp.setText(Integer.toString(upNum));
                thumbsUp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_up_selected, 0, 0, 0);
                upClicked = true;
            }
            else{
                upNum = upNum - 1;
                thumbsUp.setText(Integer.toString(upNum));
                thumbsUp.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_up, 0, 0, 0);
                upClicked = false;
            }
        }
    }

    public void requestDislike(int id, boolean dislikeyn){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/increaseLikeDisLike";
        url += "?" + "id=" + id;

        final boolean dislike;

        if(dislikeyn){
            url += "&dislikeyn=Y";
            dislike = true;
        }
        else{
            url += "&dislikeyn=N";
            dislike = false;
        }

            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(context, "응답 받음.", Toast.LENGTH_SHORT).show();
                            processDislikeResponse(response, dislike);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(context, "에러 발생." + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

            );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        //Toast.makeText(context, "영화목록 요청 보냄.", Toast.LENGTH_SHORT).show();

    }

    public void processDislikeResponse(String response, boolean dislike){
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200) {
            if(dislike){
                downNum = downNum + 1;
                thumbsDown.setText(Integer.toString(downNum));
                thumbsDown.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_down_selected, 0, 0, 0);
                downClicked = true;
            }
            else{
                downNum = downNum - 1;
                thumbsDown.setText(Integer.toString(downNum));
                thumbsDown.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_down, 0, 0, 0);
                downClicked = false;
            }
        }
    }
}
