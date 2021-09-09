package com.hanium.greenduks;

public class Qna {
    private String id;
    private String state;
    private String title;
    private String date;

    public Qna(){}

    public Qna(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public Qna(String state, String title, String date) {
        this.state = state;
        this.title = title;
        this.date = date;
    }

    public Qna(String id, String state, String title, String date) {
        this.id = id;
        this.state = state;
        this.title = title;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Qna{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
