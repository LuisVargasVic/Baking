package com.udacity.luisev96.baking.remote.tasks;

import android.os.AsyncTask;

import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.database.DatabaseRecipe;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.remote.listeners.WidgetListener;


public class WidgetTask extends AsyncTask<Recipe, Void, Recipe> {

    private BakingDatabase mBakingDatabase;
    private WidgetListener mWidgetDatabase;

    public WidgetTask(BakingDatabase bakingDatabase, WidgetListener widgetListener) {
        mBakingDatabase = bakingDatabase;
        mWidgetDatabase = widgetListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public Recipe doInBackground(Recipe... params) {
        Recipe recipe = params[0];
        try {
            final DatabaseRecipe databaseRecipe = new DatabaseRecipe(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getApp_widget_id()
            );
            mBakingDatabase.bakingDao().insertRecipe(databaseRecipe);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return recipe;
    }

    @Override
    protected void onPostExecute(Recipe recipe) {
        mWidgetDatabase.postWidgetExecute(recipe);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}