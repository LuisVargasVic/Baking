package com.udacity.luisev96.baking.presentation.recipes;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.ActivityMainBinding;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.remote.listeners.ConnectionListener;
import com.udacity.luisev96.baking.remote.listeners.RemoteListener;
import com.udacity.luisev96.baking.remote.receivers.MainReceiver;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RemoteListener, ConnectionListener {

    private ActivityMainBinding activityMainBinding;
    private MainViewModel viewModel;
    private BroadcastReceiver mReceiver;
    private MainAdapter mAdapter;
    private static ConnectionListener connectionListener;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Initialize connection receiver before making requests
        mReceiver = new MainReceiver();
        registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        connectionListener = this;

        // Set adapter before populating it
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityMainBinding.rvRecipes.setLayoutManager(layoutManager);
        activityMainBinding.rvRecipes.setHasFixedSize(true);
        mAdapter = new MainAdapter(this);
        activityMainBinding.rvRecipes.setAdapter(mAdapter);

        // Make requests
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    public static void setMainConnection(Boolean connection) {
        connectionListener.connection(connection);
    }

    @Override
    public void connection(Boolean connection) {
        viewModel.refresh( this);
        if (connection) {
            activityMainBinding.message.setText(R.string.recipes_empty);
        } else {
            activityMainBinding.message.setText(R.string.recipes_connection);
        }
    }

    @Override
    public void preExecute() {
        activityMainBinding.pb.setVisibility(View.VISIBLE);
        activityMainBinding.message.setVisibility(View.GONE);
        activityMainBinding.rvRecipes.setVisibility(View.GONE);
    }

    @Override
    public void postExecute(Boolean data) {
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "Updating list of recipes from LiveData in ViewModel");
                mAdapter.setRecipes(recipes);
                if (recipes != null && !recipes.isEmpty()) {
                    activityMainBinding.pb.setVisibility(View.GONE);
                    activityMainBinding.message.setVisibility(View.GONE);
                    activityMainBinding.rvRecipes.setVisibility(View.VISIBLE);
                } else {
                    activityMainBinding.pb.setVisibility(View.GONE);
                    activityMainBinding.message.setVisibility(View.VISIBLE);
                    activityMainBinding.rvRecipes.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
