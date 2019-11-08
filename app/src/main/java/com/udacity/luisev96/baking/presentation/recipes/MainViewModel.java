package com.udacity.luisev96.baking.presentation.recipes;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.luisev96.baking.data.BakingRepository;
import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.remote.listeners.RemoteListener;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private BakingRepository repository;
    private LiveData<List<Recipe>> recipes;

    public MainViewModel(Application application) {
        super(application);
        BakingDatabase database = BakingDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the recipes from the DataBase");
        repository = new BakingRepository(database);
        recipes = repository.getRecipes();
    }

    void refresh(RemoteListener remoteListener) {
        repository.refresh(remoteListener);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

}