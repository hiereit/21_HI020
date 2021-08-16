package com.hanium.greenduks;

public class CenterMain {
    private int percentage;
    private String addr;
    private int boxId;

    public CenterMain(){}

    public CenterMain(int percentage, String addr, int boxId) {
        this.percentage = percentage;
        this.addr = addr;
        this.boxId = boxId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }
}
