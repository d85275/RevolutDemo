package com.e.revolutdemo;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.e.revolutdemo.models.DataModel;
import com.e.revolutdemo.remote.DataService;
import com.e.revolutdemo.repository.DataRepository;
import com.e.revolutdemo.viewmodel.MyViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;


import java.util.concurrent.TimeUnit;


import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


import static junit.framework.TestCase.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestMyViewModel {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private DataService dataService = mock(DataService.class);
    private DataRepository repository = new DataRepository(dataService);
    private MyViewModel viewModel;
    private Observer<Integer> errorObserver = mock(Observer.class);

    private Observer<DataModel> successObserver = mock(Observer.class);


    @Before
    public void before() {
        viewModel = new MyViewModel(repository);
        viewModel.getErrorMessage().observeForever(errorObserver);
        viewModel.getRateData().observeForever(successObserver);

        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, false);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Test
    public void testErrorMessage() {
        int expectedResult = 1;
        viewModel.set_errorMessage(expectedResult);

        ArgumentCaptor captor = ArgumentCaptor.forClass(Integer.class);
        verify(errorObserver, times(1)).onChanged((Integer) captor.capture());

        assertEquals(expectedResult, captor.getValue());
    }

    @Test
    public void testLoadData() {
        DataModel expectedResult = new DataModel("1", "2", null);
        Single<DataModel> single = Single.just(expectedResult);
        when(dataService.getData()).thenReturn(single);
        viewModel.loadData();

        ArgumentCaptor captor = ArgumentCaptor.forClass(DataModel.class);
        verify(successObserver, times(1)).onChanged((DataModel) captor.capture());

        DataModel result = (DataModel) captor.getValue();
        assertEquals(result, expectedResult);
    }
}
