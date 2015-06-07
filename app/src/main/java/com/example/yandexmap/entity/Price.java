package com.example.yandexmap.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by voronnenok on 05.06.15.
 */
public class Price implements Parcelable{
    private final int price;
    private final String description;

    public Price(int price, String description) {
        this.price = price;
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public static final Parcelable.Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source.readInt(), source.readString());
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeString(description);
    }
}
