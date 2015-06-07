package com.example.yandexmap.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by voronnenok on 05.06.15.
 */
public class Location implements Parcelable {
    private Double lat;
    private Double lon;

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    public static final Parcelable.Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            Location location = new Location();
            location.lat = source.readDouble();
            location.lon = source.readDouble();
            return location;
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }
}
