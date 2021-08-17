package com.hanium.greenduks;

public class Ranking {
    private int rankImg;
    private int rank;
    private String nickname;
    private int weight;

    public Ranking(){}

    public Ranking(int rank, String nickname, int weight) {
        this.rank = rank;
        this.nickname = nickname;
        this.weight = weight;
    }

    public Ranking(int rankImg, int rank, String nickname, int weight) {
        this.rankImg = rankImg;
        this.rank = rank;
        this.nickname = nickname;
        this.weight = weight;
    }

    public int getRankImg() {
        return rankImg;
    }

    public void setRankImg(int rankImg) {
        this.rankImg = rankImg;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
