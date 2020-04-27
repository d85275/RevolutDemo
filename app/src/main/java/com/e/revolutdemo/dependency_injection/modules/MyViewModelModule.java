package com.e.revolutdemo.dependency_injection.modules;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.e.revolutdemo.viewmodel.MyViewModel;
import com.e.revolutdemo.dependency_injection.ViewModelKey;
import com.e.revolutdemo.viewmodel.MyViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel.class)
    abstract ViewModel bindViewModel(MyViewModel myViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindFactory(MyViewModelFactory factory);
}
