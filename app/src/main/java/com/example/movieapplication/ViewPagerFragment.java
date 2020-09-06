package com.example.movieapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.movieapplication.data.MovieInfo;
import com.example.movieapplication.data.MovieList;
import com.example.movieapplication.data.ResponseInfo;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerFragment extends Fragment {

    private Context context;
    MoviePagerAdapter adapter;
    ViewPager pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_view_pager, container, false);

        context = container.getContext();

        pager = (ViewPager)rootView.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(5);

        adapter = new MoviePagerAdapter(getFragmentManager());

        requestMovieList();

        return rootView;
    }

    public void requestMovieList(){
        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovieList";
        url += "?" + "type=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(context, "응답 받음.", Toast.LENGTH_SHORT).show();
                        processResponse(response);
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

    public void processResponse(String response){
        Gson gson = new Gson();
        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200){
            MovieList movieList = gson.fromJson(response, MovieList.class);

            for(int i=0; i<movieList.result.size(); i++){
                MovieFragment fragment = new MovieFragment();
                MovieInfo movieInfo = movieList.result.get(i);
                Bundle bundle = new Bundle(4);
                bundle.putInt("id", movieInfo.id);
                bundle.putString("image", movieInfo.image);
                bundle.putString("name", movieInfo.id + ". " +movieInfo.title);
                bundle.putString("info", "예매율 " + movieInfo.reservation_rate + "% | " + movieInfo.grade + "세 관람가");
                fragment.setArguments(bundle);
                adapter.addItem(fragment);
            }
            //Toast.makeText(context, "영화 갯수 : " + movieList.result.size(), Toast.LENGTH_SHORT).show();
            pager.setAdapter(adapter);
        }

    }
}
