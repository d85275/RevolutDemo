package com.e.revolutdemo;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.blongho.country_data.World;
import com.e.revolutdemo.currency_data.Currency;
import com.e.revolutdemo.currency_data.CurrencyList;
import com.e.revolutdemo.dependency_injection.component.DaggerAppComponent;
import com.e.revolutdemo.models.DataModel;
import com.e.revolutdemo.viewmodel.MyViewModel;
import com.e.revolutdemo.viewmodel.MyViewModelFactory;


import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements IMoveItemCallback {
    private RecyclerView rvRates;
    private RateAdapter adapter;


    private MyViewModel viewModel;

    @Inject
    MyViewModelFactory viewModelFactory;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rvRates = findViewById(R.id.rvRates);

        // show the progress dialog before we get our first data
        pd = new ProgressDialog(this);
        pd.setTitle(R.string.loading);
        pd.show();
        pd.setCanceledOnTouchOutside(false);

        // load flags
        World.init(getApplicationContext());

        // field injection
        DaggerAppComponent.create().inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyViewModel.class);

        // set observer for data and error messages
        viewModel.getData().observe(MainActivity.this, this::onRateChanged);
        viewModel.getErrorMessage().observe(MainActivity.this, this::onError);

        // load
        viewModel.loadData();
    }

    private void onRateChanged(DataModel dataModel) {
        if (adapter == null) {
            ArrayList<Currency> currencies = new CurrencyList().getList();
            setAdapter(currencies, dataModel.getMap());
        } else {
            adapter.setMap(dataModel.getMap());
        }

        // dismiss the progress dialog
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    private void onError(int stringId) {
        AlertDialog alError = new AlertDialog.Builder(MainActivity.this).create();
        alError.setView(getErrorView(stringId));

        alError.setButton(AlertDialog.BUTTON_POSITIVE, getText(R.string.bt_retry), (dialogInterface, i) -> {
            // try to update the data if the user wants to retry
            viewModel.getData();
        });
        alError.setButton(AlertDialog.BUTTON_NEGATIVE, getText(R.string.bt_cancel), (dialogInterface, i) -> {
            // do nothing if cancel button is pressed, just dismiss the progress dialog if necessary
            if (pd.isShowing()) {
                pd.dismiss();
            }
        });
        alError.show();
    }


    private View getErrorView(int stringId) {
        TextView errorView = new TextView(MainActivity.this);
        errorView.setText(stringId);
        errorView.setTextSize(14);
        errorView.setHeight(400);
        errorView.setGravity(Gravity.CENTER);
        errorView.setTextColor(Color.DKGRAY);
        return errorView;
    }

    private void setAdapter(ArrayList<Currency> list, HashMap<String, Double> map) {
        // use this setting to improve performance because we do not intent
        // to change the layout size of the RecyclerView
        rvRates.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRates.setLayoutManager(mLayoutManager);

        adapter = new RateAdapter(list, map, this, this);
        rvRates.setAdapter(adapter);

        // hide the keyboard when the user scrolls the list
        rvRates.setOnTouchListener(touchListener);
    }


    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            viewModel.recyclerViewTouched(view, imm);
            return false;
        }
    };

    @Override
    public void itemMoved() {
        // scroll to the top when an item is moved
        if (rvRates.getLayoutManager() != null) {
            rvRates.getLayoutManager().scrollToPosition(0);
        }
    }
}
