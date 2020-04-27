package com.e.revolutdemo.viewmodel;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.e.revolutdemo.R;
import com.e.revolutdemo.models.DataModel;
import com.e.revolutdemo.repository.DataRepository;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import io.reactivex.schedulers.Schedulers;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Integer> _errorMessage = new MutableLiveData<>();
    private MutableLiveData<DataModel> _rateData = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private DataRepository repository;


    @Inject
    public MyViewModel(DataRepository repository) {
        this.repository = repository;
        String strin = "";
        strin.compareTo(strin);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public MutableLiveData<DataModel> getData() {
        if (_rateData == null) {
            _rateData = new MutableLiveData<>();
        }
        //loadData();
        return _rateData;
    }

    public LiveData<DataModel> getRateData() {
        return _rateData;
    }

    public void set_errorMessage(int errorMessage) {
        _errorMessage.setValue(errorMessage);
    }

    public LiveData<Integer> getErrorMessage() {
        return _errorMessage;
    }

    public void loadData() {
        Disposable subscribe = repository.dataModelSingle()
                .delay(1, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    if (isSameCause(throwable, UnknownHostException.class)) {
                        _errorMessage.setValue(R.string.no_network);
                    } else {
                        // other error messages
                        _errorMessage.setValue(R.string.error);
                    }
                })
                .subscribe(dataModel -> getData().setValue(dataModel));

        disposable.add(subscribe);
    }

    private boolean isSameCause(Throwable e, Class<? extends Throwable> cl) {
        return cl.isInstance(e) || e.getCause() != null && isSameCause(e.getCause(), cl);
    }

    public void recyclerViewTouched(View view, InputMethodManager imm) {
        // if imm is not null and the keyboard is shown, dismiss the keyboard
        if (imm != null && imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
