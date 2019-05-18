package com.example.finalproject.RecyclerView;

public class Trips {

    private String mName, mLocatie, mImagine;

    public Trips(String mName, String mLocatie) {
        this.mName = mName;
        this.mLocatie = mLocatie;
    }

    public String getmName() {
        return mName;
    }

    public String getmLocatie() {
        return mLocatie;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmLocatie(String mLocatie) {
        this.mLocatie = mLocatie;
    }
}
