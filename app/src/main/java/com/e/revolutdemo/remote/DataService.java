package com.e.revolutdemo.remote;

import com.e.revolutdemo.models.DataModel;

import io.reactivex.Single;

import retrofit2.http.GET;

public interface DataService {
    @GET("latest?base=EUR")
    Single<DataModel> getData();

}
