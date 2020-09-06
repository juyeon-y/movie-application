package com.example.movieapplication.data;

/*
message: "movie readMovieList 성공",
code: 200,
resultType: "list",
result

여기도 세 response 다 받아올수 있게끔 합쳐놨음.
*/

public class ResponseInfo {

    public String message;
    public int code;
    public String resultType;
    public int totalCount;
}
