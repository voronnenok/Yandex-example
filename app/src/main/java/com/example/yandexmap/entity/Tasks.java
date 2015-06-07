package com.example.yandexmap.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voronnenok on 05.06.15.
 */
public class Tasks implements Parcelable{
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public static final Parcelable.Creator<Tasks> CREATOR = new Creator<Tasks>() {
        @Override
        public Tasks createFromParcel(Parcel source) {
            Tasks tasks = new Tasks();
            tasks.tasks = new ArrayList<>();
            source.readTypedList(tasks.tasks, Task.CREATOR);
            return tasks;
        }

        @Override
        public Tasks[] newArray(int size) {
            return new Tasks[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(tasks);
    }
}
