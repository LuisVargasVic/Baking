package com.udacity.luisev96.baking.data;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.database.DatabaseRecipe;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.remote.listeners.RemoteListener;
import com.udacity.luisev96.baking.remote.tasks.BakingTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BakingRepository {

    private BakingDatabase mBakingDatabase;

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public BakingRepository(BakingDatabase bakingDatabase) {
        mBakingDatabase = bakingDatabase;
    }

    public void refresh(RemoteListener remoteListener) {
        URL url = null;
        try {
            url = new URL(BASE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        final URL finalUrl = url;
        new BakingTask(mBakingDatabase, remoteListener).execute(finalUrl);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return Transformations.map(mBakingDatabase.bakingDao().getRecipes(),
                new Function<List<DatabaseRecipe>, List<Recipe>>() {
                    @Override
                    public List<Recipe> apply(List<DatabaseRecipe> databaseRecipes) {
                        List<Recipe> recipes = new ArrayList<>();

                        for (int i = 0; i < databaseRecipes.size(); i++) {
                            DatabaseRecipe databaseRecipe = databaseRecipes.get(i);
                            recipes.add(new Recipe(
                                            databaseRecipe.getId(),
                                            databaseRecipe.getName()
                                    )
                            );
                        }

                        return recipes;
                    }
                });
    }
}