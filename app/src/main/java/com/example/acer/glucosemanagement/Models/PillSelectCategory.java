package com.example.acer.glucosemanagement.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class PillSelectCategory implements Parcelable {

    String pillsName;
    String pillsAmount;

    public PillSelectCategory(String pillsName, String pillsAmount) {
        this.pillsName = pillsName;
        this.pillsAmount = pillsAmount;
    }

    protected PillSelectCategory(Parcel in) {
        pillsName = in.readString();
        pillsAmount = in.readString();
    }

    public static final Creator<PillSelectCategory> CREATOR = new Creator<PillSelectCategory>() {
        @Override
        public PillSelectCategory createFromParcel(Parcel in) {
            return new PillSelectCategory(in);
        }

        @Override
        public PillSelectCategory[] newArray(int size) {
            return new PillSelectCategory[size];
        }
    };

    public String getPillsName() {
        return pillsName;
    }

    public String getPillsAmount() {
        return pillsAmount;
    }

    public void setPillsName(String pillsName) {
        this.pillsName = pillsName;
    }

    public void setPillsAmount(String pillsAmount) {
        this.pillsAmount = pillsAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pillsName);
        dest.writeString(pillsAmount);
    }
}
