package com.udacity.luisev96.baking.presentation.detail.steps;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.luisev96.baking.data.BakingRepository;
import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.domain.Step;

public class StepViewModel extends AndroidViewModel {

    private static final String TAG = StepViewModel.class.getSimpleName();
    private BakingRepository repository;
    private LiveData<Step> step;

    public StepViewModel(Application application) {
        super(application);
        BakingDatabase database = BakingDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the step from the DataBase");
        repository = new BakingRepository(database);
    }

    void init(int recipe_id, int step_id) {
        step = repository.getStep(recipe_id, step_id);
    }

    LiveData<Step> getStep() {
        return step;
    }

}
