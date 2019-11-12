package com.udacity.luisev96.baking.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.ActivityWidgetBinding;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.remote.listeners.ConnectionListener;
import com.udacity.luisev96.baking.remote.listeners.RemoteListener;
import com.udacity.luisev96.baking.remote.listeners.WidgetListener;
import com.udacity.luisev96.baking.remote.receivers.WidgetReceiver;
import com.udacity.luisev96.baking.utils.WidgetProvider;

import java.util.List;


public class WidgetActivity extends AppCompatActivity implements RemoteListener, ConnectionListener, WidgetListener, WidgetAdapter.OnRecipeClickListener {

    private ActivityWidgetBinding activityWidgetBinding;
    private WidgetViewModel viewModel;
    private BroadcastReceiver mReceiver;
    private WidgetAdapter mAdapter;
    private static ConnectionListener connectionListener;
    private int mAppWidgetId;
    private static final String TAG = WidgetActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWidgetBinding = DataBindingUtil.setContentView(this, R.layout.activity_widget);
        setSupportActionBar(activityWidgetBinding.toolbar);
        setTitle(getString(R.string.widget));

        // Initialize connection receiver before making requests
        mReceiver = new WidgetReceiver();
        registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        connectionListener = this;

        if (getIntent() != null)
            if (getIntent().hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) mAppWidgetId = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        // Set adapter before populating it
        if (getString(R.string.screen).equals("Phone")) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            activityWidgetBinding.rvRecipes.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            activityWidgetBinding.rvRecipes.setLayoutManager(layoutManager);
        }
        activityWidgetBinding.rvRecipes.setHasFixedSize(true);
        mAdapter = new WidgetAdapter(this);
        activityWidgetBinding.rvRecipes.setAdapter(mAdapter);

        // Make requests
        viewModel = ViewModelProviders.of(this).get(WidgetViewModel.class);

    }

    public static void setWidgetConnection(Boolean connection) {
        connectionListener.connection(connection);
    }

    @Override
    public void connection(Boolean connection) {
        viewModel.refresh( this);
        if (connection) {
            activityWidgetBinding.message.setText(R.string.recipes_empty);
        } else {
            activityWidgetBinding.message.setText(R.string.recipes_connection);
        }
    }

    @Override
    public void preExecute() {
        activityWidgetBinding.pb.setVisibility(View.VISIBLE);
        activityWidgetBinding.message.setVisibility(View.GONE);
        activityWidgetBinding.rvRecipes.setVisibility(View.GONE);
    }

    @Override
    public void postExecute(Boolean data) {
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.d(TAG, "Updating list of recipes from LiveData in ViewModel");
                mAdapter.setRecipes(recipes);
                if (recipes != null && !recipes.isEmpty()) {
                    activityWidgetBinding.pb.setVisibility(View.GONE);
                    activityWidgetBinding.message.setVisibility(View.GONE);
                    activityWidgetBinding.rvRecipes.setVisibility(View.VISIBLE);
                } else {
                    activityWidgetBinding.pb.setVisibility(View.GONE);
                    activityWidgetBinding.message.setVisibility(View.VISIBLE);
                    activityWidgetBinding.rvRecipes.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void postWidgetExecute(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        WidgetProvider.updateWidget(this, appWidgetManager, recipe);
        Intent widgetIntent = new Intent();
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, widgetIntent);
        finish();
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Recipe mRecipe = new Recipe(recipe.getId(), recipe.getName(), mAppWidgetId);
        viewModel.insertWidget(mRecipe, this);
    }
}
