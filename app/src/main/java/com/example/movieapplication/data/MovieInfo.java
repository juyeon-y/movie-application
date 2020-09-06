package com.example.movieapplication.data;

/*
        id: 1,
        title: "꾼",
        title_eng: "The Swindlers",
        date: "2017-11-22",
        user_rating: 4.1,
        audience_rating: 8.36,
        reviewer_rating: 4.33,
        reservation_rate: 61.69,
        reservation_grade: 1,
        grade: 15,
        thumb: "http://movie2.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg?type=m99_141_2",
        image: "http://movie.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg"

        결론적으로 movielist랑 movie 상세정보 항목들 합쳐놨음.
 */

public class MovieInfo {
    /*
    public int id;
    public String title;
    public String title_eng;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;
    public String image;
     */

    public String title;
    public int id;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;
    public String image;
    public String photos;
    public String videos;
    public String outlinks;
    public String genre;
    public int duration;
    public int audience;
    public String synopsis;
    public String director;
    public String actor;
    public int like;
    public int dislike;
}
