package com.udacity.luisev96.baking.remote.tasks;

import android.os.AsyncTask;

import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.database.DatabaseWidget;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.remote.listeners.WidgetListener;


public class WidgetInsertTask extends AsyncTask<Recipe, Void, Recipe> {

    private final BakingDatabase mBakingDatabase;
    private final WidgetListener mWidgetDatabase;

    public WidgetInsertTask(BakingDatabase bakingDatabase, WidgetListener widgetListener) {
        mBakingDatabase = bakingDatabase;
        mWidgetDatabase = widgetListener;
    }

    @Override
    public Recipe doInBackground(Recipe... params) {
        Recipe recipe = params[0];
        try {
            final DatabaseWidget databaseRecipe = new DatabaseWidget(
                    recipe.getApp_widget_id(),
                    recipe.getId(),
                    recipe.getName()
            );
            mBakingDatabase.bakingDao().insertWidget(databaseRecipe);

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
}