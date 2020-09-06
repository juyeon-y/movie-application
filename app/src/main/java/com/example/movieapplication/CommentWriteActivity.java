package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.movieapplication.data.MovieInfo;
import com.example.movieapplication.data.MovieList;
import com.example.movieapplication.data.ResponseInfo;
import com.google.gson.Gson;

public class CommentWriteActivity extends AppCompatActivity {
    RatingBar ratingBar;
    EditText contentsInput;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        contentsInput = (EditText) findViewById(R.id.contentsInput);

        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                requestCreateComment(id);
                finish();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });


    }

    public void requestCreateComment(int id){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/createComment";
        url += "?" + "id=" + id + "&writer=ppap" + "&rating=" + ratingBar.getRating()*2 + "&contents=" + contentsInput.getText().toString();

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
            Toast.makeText(getApplicationContext(), "comment저장함.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    public void returnToMain(){

        Intent intent = new Intent();
        intent.putExtra("success", true);
        setResult(RESULT_OK, intent);
        finish();
    }
     */
}
