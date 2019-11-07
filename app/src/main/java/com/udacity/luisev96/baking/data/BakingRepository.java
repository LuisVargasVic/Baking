package com.udacity.luisev96.baking.data;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.database.DatabaseIngredient;
import com.udacity.luisev96.baking.database.DatabaseRecipe;
import com.udacity.luisev96.baking.database.DatabaseStep;
import com.udacity.luisev96.baking.domain.Ingredient;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.domain.Step;
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

    public LiveData<List<Step>> getSteps(int recipe_id) {
        return Transformations.map(mBakingDatabase.bakingDao().getSteps(recipe_id),
                new Function<List<DatabaseStep>, List<Step>>() {
                    @Override
                    public List<Step> apply(List<DatabaseStep> databaseSteps) {
                        List<Step> steps = new ArrayList<>();

                        for (int i = 0; i < databaseSteps.size(); i++) {
                            DatabaseStep databaseStep = databaseSteps.get(i);
                            steps.add(new Step(
                                            databaseStep.getId(),
                                            databaseStep.getShortDescription(),
                                            databaseStep.getDescription(),
                                            databaseStep.getVideo_url(),
                                            databaseStep.getThumbnail_url(),
                                            databaseStep.getRecipe_id()
                                    )
                            );
                        }

                        return steps;
                    }
                });
    }

    public LiveData<List<Ingredient>> getIngredients(int recipe_id) {
        return Transformations.map(mBakingDatabase.bakingDao().getIngredients(recipe_id),
                new Function<List<DatabaseIngredient>, List<Ingredient>>() {
                    @Override
                    public List<Ingredient> apply(List<DatabaseIngredient> databaseIngredients) {
                        List<Ingredient> ingredients = new ArrayList<>();

                        for (int i = 0; i < databaseIngredients.size(); i++) {
                            DatabaseIngredient databaseIngredient = databaseIngredients.get(i);
                            ingredients.add(new Ingredient(
                                            databaseIngredient.getQuantity(),
                                            databaseIngredient.getMeasure(),
                                            databaseIngredient.getIngredient(),
                                            databaseIngredient.getRecipe_id()
                                    )
                            );
                        }

                        return ingredients;
                    }
                });
    }
}