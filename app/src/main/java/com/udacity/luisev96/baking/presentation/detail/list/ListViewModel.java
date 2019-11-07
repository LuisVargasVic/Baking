package com.udacity.luisev96.baking.presentation.detail.list;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.luisev96.baking.data.BakingRepository;
import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.domain.Step;

import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private static final String TAG = ListViewModel.class.getSimpleName();
    private BakingRepository repository;
    private LiveData<List<Step>> steps;

    public ListViewModel(Application application) {
        super(application);
        BakingDatabase database = BakingDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the steps from the DataBase");
        repository = new BakingRepository(database);
    }

    public void init(int recipe_id) {
        steps = repository.getSteps(recipe_id);
    }

    public LiveData<List<Step>> getSteps() {
        return steps;
    }

}