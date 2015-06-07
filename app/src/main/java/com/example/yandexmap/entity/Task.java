package com.example.yandexmap.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voronnenok on 05.06.15.
 */
public class Task implements Parcelable{
    private Integer ID;
    private String title;
    private Long date;
    private String text;
    private String longText;
    private String durationLimitText;
    private Integer price;
    private String locationText;
    private Location location;
    private Integer zoomLevel;
    private List<Price> prices;
    private Boolean translation;

    public int getId() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getLongText() {
        return longText;
    }

    public String getDurationLimitText() {
        return durationLimitText;
    }

    public int getPrice() {
        return price;
    }

    public String getLocationText() {
        return locationText;
    }

    public Location getLocation() {
        return location;
    }

    public int getZoomLevel() {
        return zoomLevel;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public boolean getTranslation() {
        return translation;
    }

    public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            Task task = new Task();
            task.ID = source.readInt();
            task.title = source.readString();
            task.date = source.readLong();
            task.text = source.readString();
            task.longText = source.readString();
            task.durationLimitText = source.readString();
            task.price = source.readInt();
            task.locationText = source.readString();
            task.location = source.readParcelable(Location.class.getClassLoader());
            task.zoomLevel = source.readInt();
            task.prices = new ArrayList<>();
            source.readTypedList(task.prices, Price.CREATOR);
            task.translation = source.readInt() == 1;
            return task;
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(title);
        dest.writeLong(date);
        dest.writeString(text);
        dest.writeString(longText);
        dest.writeString(durationLimitText);
        dest.writeInt(price);
        dest.writeString(locationText);
        dest.writeParcelable(location, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeInt(zoomLevel);
        dest.writeTypedList(prices);
        dest.writeInt(translation ? 1 : 0);
    }
}
