package com.gdou.movieshop;

public class DetailsInfo {
    protected String time = "17:20";
    protected String room = "3号激光厅";
    protected String price = "26.9";
    protected static final String TIME_PREFIX = "Time_";
    protected static final String ROOM_PREFIX = "Room_";
    protected static final String PRICE_PREFIX= "Price_";

    public DetailsInfo(String time,String room,String price){
        this.time=time;
        this.room=room;
        this.price=price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public static String getTimePrefix() {
        return TIME_PREFIX;
    }

    public static String getRoomPrefix() {
        return ROOM_PREFIX;
    }

    public static String getDescPrefix() {
        return PRICE_PREFIX;
    }
}
