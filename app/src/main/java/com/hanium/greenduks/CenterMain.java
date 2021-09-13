package com.hanium.greenduks;

public class CenterMain {
    private double weight;
    private String addr;
    private String boxId;

    public CenterMain(){}

    public CenterMain(double weight, String addr, String boxId) {
        this.weight = weight;
        this.addr = addr;
        this.boxId = boxId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }
}
