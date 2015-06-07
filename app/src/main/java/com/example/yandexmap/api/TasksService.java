package com.example.yandexmap.api;

import com.example.yandexmap.entity.Tasks;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by voronnenok on 05.06.15.
 */
public interface TasksService {
    @GET("/tasks")
    void getTasks(Callback<Tasks> tasks);
}

