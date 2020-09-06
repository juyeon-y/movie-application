package com.example.movieapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.example.movieapplication.data.MovieList;
import com.example.movieapplication.data.ResponseInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class MovieListActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Context context = this;
    FrameLayout frameLayout;
    //ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_menu);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.movieList){
                    Intent intent = new Intent(getApplicationContext(), MovieListActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.movieAPI){
                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.booking){
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);



        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        //requestMovieList();

        ViewPagerFragment pagerFragment = new ViewPagerFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, pagerFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeToDetailFragment(int id){
        MovieDetailFragment detailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt("id", id);
        detailFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, detailFragment);
        fragmentTransaction.commit();
    }

    /*
    public void requestMovieList(){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "응답 받음.", Toast.LENGTH_SHORT).show();
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "에러 발생." + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        Toast.makeText(context, "영화목록 요청 보냄.", Toast.LENGTH_SHORT).show();

    }

    public void processResponse(String response){
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200){
            MovieList movieList = gson.fromJson(response, MovieList.class);
            Toast.makeText(context, "영화 갯수 : " + movieList.result.size(), Toast.LENGTH_SHORT).show();
        }

    }

     */
}
