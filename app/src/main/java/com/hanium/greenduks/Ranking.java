package com.hanium.greenduks;

public class Ranking {
    private int rank;
    private String memberId;
    private int weight;

    public Ranking(){}

    public Ranking(int rank, String memberId, int weight) {
        this.rank = rank;
        this.memberId = memberId;
        this.weight = weight;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
