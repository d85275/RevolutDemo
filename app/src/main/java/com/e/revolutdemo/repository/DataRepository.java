package com.e.revolutdemo.repository;

import com.e.revolutdemo.models.DataModel;
import com.e.revolutdemo.remote.DataService;

import javax.inject.Inject;

import io.reactivex.Single;

public class DataRepository {

    private DataService service;

    @Inject
    public DataRepository(DataService service) {
        this.service = service;
    }

    public Single<DataModel> dataModelSingle() {
        return service.getData();
    }
}
