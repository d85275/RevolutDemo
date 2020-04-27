package com.e.revolutdemo.dependency_injection.component;

import com.e.revolutdemo.MainActivity;
import com.e.revolutdemo.dependency_injection.modules.ContextModule;
import com.e.revolutdemo.dependency_injection.modules.NetworkModule;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {NetworkModule.class, ContextModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
