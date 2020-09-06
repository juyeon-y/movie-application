package com.example.movieapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentData implements Parcelable {

    String comment;
    float ratingNum;

    public CommentData(String comment, float ratingNum) {
        this.comment = comment;
        this.ratingNum = ratingNum;
    }

    public CommentData(Parcel src){
        comment = src.readString();
        ratingNum = src.readFloat();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public CommentData createFromParcel(Parcel src){
            return new CommentData(src);
        }

        public CommentData[] newArray(int size){
            return new CommentData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeFloat(ratingNum);
    }
}
