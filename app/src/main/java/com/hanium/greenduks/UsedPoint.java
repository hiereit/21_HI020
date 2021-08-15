package com.hanium.greenduks;

public class UsedPoint {

    private int point;
    private String content;
    private String date;

    public UsedPoint(){}

    public UsedPoint(int point, String content, String date) {
        this.point = point;
        this.content = content;
        this.date = date;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
