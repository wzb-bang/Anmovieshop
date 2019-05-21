package com.gdou.movieshop;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.Button;

public class MovieInfo {
    protected String movie_name = "大侦探皮卡丘";
    protected String movie_score = "6.7";
    protected String actor = " 瑞恩·雷诺兹 / 贾斯蒂斯·史密斯 / 凯瑟琳·纽顿";
    protected Drawable image;
//    protected String bt_id;
    protected static final String NAME_PREFIX = "";
    protected static final String SCORE_PREFIX = "评分：";
    protected static final String ACTOR_PREFIX = "主演：";
//    protected static final String BT_ID_PREFIX = "bt_";

    public MovieInfo(String movie_name, String movie_score, String actor,Drawable image){
        this.movie_name = movie_name;
        this.movie_score = movie_score;
        this.actor = actor;
        this.image=image;
    }

//    public String getBt_id() {
//        return bt_id;
//    }
//
//    public void setBt_id(String bt_id) {
//        this.bt_id = bt_id;
//    }
//
//    public static String getBtIdPrefix() {
//        return BT_ID_PREFIX;
//    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_score() {
        return movie_score;
    }

    public void setMovie_score(String movie_score) {
        this.movie_score = movie_score;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public static String getNamePrefix() {
        return NAME_PREFIX;
    }

    public static String getScorePrefix() {
        return SCORE_PREFIX;
    }

    public static String getActorPrefix() {
        return ACTOR_PREFIX;
    }
}