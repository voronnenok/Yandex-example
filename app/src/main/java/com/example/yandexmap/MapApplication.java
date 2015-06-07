package com.example.yandexmap;

import android.app.Application;

import com.example.yandexmap.api.TasksService;

import retrofit.RestAdapter;

/**
 * Created by voronnenok on 05.06.15.
 */
public class MapApplication extends Application {

    private TasksService tasksService;

    @Override
    public void onCreate() {
        super.onCreate();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://test.boloid.com:9000")
                .build();

        tasksService = restAdapter.create(TasksService.class);
    }

    public TasksService getTasksService() {
        return tasksService;
    }
}
