package com.example.acer.glucosemanagement.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class RowCategory implements Parcelable {

    Integer itemId;
    Integer imgUrl;
    String type;
    String date;
    String time;
    String value;
    String subValue;
    String unit;



    public RowCategory(Integer itemId, Integer imgUrl, String date, String type, String time, String value, String subValue, String unit) {
        this.itemId = itemId;
        this.imgUrl = imgUrl;
        this.value = value;
        this.subValue = subValue;
        this.type = type;
        this.unit = unit;
        this.time = time;
        this.date = date;
    }

    protected RowCategory(Parcel in) {
        if (in.readByte() == 0) {
            itemId = null;
        } else {
            itemId = in.readInt();
        }
        if (in.readByte() == 0) {
            imgUrl = null;
        } else {
            imgUrl = in.readInt();
        }
        type = in.readString();
        date = in.readString();
        time = in.readString();
        value = in.readString();
        subValue = in.readString();
        unit = in.readString();
    }

    public static final Creator<RowCategory> CREATOR = new Creator<RowCategory>() {
        @Override
        public RowCategory createFromParcel(Parcel in) {
            return new RowCategory(in);
        }

        @Override
        public RowCategory[] newArray(int size) {
            return new RowCategory[size];
        }
    };

    public String getSubValue() {
        return subValue;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (itemId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemId);
        }
        if (imgUrl == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imgUrl);
        }
        dest.writeString(type);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(value);
        dest.writeString(subValue);
        dest.writeString(unit);
    }
}
