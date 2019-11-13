package com.udacity.luisev96.baking.presentation.detail.ingredients;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.udacity.luisev96.baking.data.BakingRepository;
import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.domain.Ingredient;

import java.util.List;

public class IngredientsViewModel extends AndroidViewModel {

    private static final String TAG = IngredientsViewModel.class.getSimpleName();
    private final BakingRepository repository;
    private LiveData<List<Ingredient>> ingredients;

    public IngredientsViewModel(Application application) {
        super(application);
        BakingDatabase database = BakingDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the ingredients from the DataBase");
        repository = new BakingRepository(database);
    }

    void init(int recipe_id) {
        ingredients = repository.getIngredients(recipe_id);
    }

    LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

}