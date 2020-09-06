package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.movieapplication.data.CommentInfo;
import com.example.movieapplication.data.CommentList;
import com.example.movieapplication.data.ResponseInfo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SeeMoreActivity extends AppCompatActivity {

    ListView listView;
    int id;
    //ArrayList<CommentData> commentList;
    //ArrayList<CommentData> newCommentList = new ArrayList<CommentData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        listView = (ListView)findViewById(R.id.listView);


        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");


        //adapter.addItem(new FilmCommentItem(commentInfo.writer, date, commentInfo.contents, R.drawable.user1, commentInfo.rating, "추천 " + commentInfo.recommend, "신고하기"));
        /*
        Intent passedIntent = getIntent();
        processIntent(passedIntent);
         */

        TextView writeComment = (TextView)findViewById(R.id.writeComment);
        writeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentWriteActivity();
            }
        });

    }

    /*
    @Override
    public void onBackPressed() {
        returnToMain();
    }
     */

    public void showCommentWriteActivity(){
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void requestCommentList(int id){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readCommentList";
        url += "?" + "id=" + id + "&limit=all";

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
            FilmCommentAdapter adapter = new FilmCommentAdapter(getApplicationContext());

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

    @Override
    protected void onStart() {
        super.onStart();
        requestCommentList(id);
    }

    /*
    private void processIntent(Intent intent){
        if(intent != null){
            commentList = intent.getParcelableArrayListExtra("comments");
            if(commentList != null){
                for(int i=0; i<commentList.size(); i++){
                    adapter.addItem(new FilmCommentItem("lym81**", "12분전", commentList.get(i).comment, R.drawable.user1, commentList.get(i).ratingNum, "추천 2", "신고하기"));
                }
                listView.setAdapter(adapter);
            }
        }
    }
     */

    /*
    public void returnToMain(){
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("newComments", newCommentList);

        setResult(RESULT_OK, intent);

        finish();
    }
     */
}
