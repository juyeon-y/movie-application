package com.example.movieapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MovieFragment extends Fragment {
    ImageView poster;
    TextView movieName;
    TextView movieInfo;
    Button see_detail_button;

    private int id;

    MovieListActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MovieListActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_movie, container, false);

        poster = (ImageView) rootView.findViewById(R.id.poster);
        movieName = (TextView) rootView.findViewById(R.id.movieName);
        movieInfo = (TextView) rootView.findViewById(R.id.movieInfo);
        see_detail_button = (Button) rootView.findViewById(R.id.see_detail_button);

        String image = getArguments().getString("image");
        String name = getArguments().getString("name");
        String info = getArguments().getString("info");
        id = getArguments().getInt("id");

        see_detail_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.changeToDetailFragment(id);
            }
        });

        Glide.with(this).load(image).into(poster);

        movieName.setText(name);
        movieInfo.setText(info);

        return rootView;
    }
}
