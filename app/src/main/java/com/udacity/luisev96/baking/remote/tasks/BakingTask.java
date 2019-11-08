package com.udacity.luisev96.baking.remote.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.database.DatabaseIngredient;
import com.udacity.luisev96.baking.database.DatabaseRecipe;
import com.udacity.luisev96.baking.database.DatabaseStep;
import com.udacity.luisev96.baking.domain.Ingredient;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.domain.Step;
import com.udacity.luisev96.baking.remote.listeners.RemoteListener;
import com.udacity.luisev96.baking.utils.JsonUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BakingTask extends AsyncTask<URL, Void, Boolean> {

    private BakingDatabase mBakingDatabase;
    private RemoteListener mRemoteListener;
    private static final String TAG = BakingTask.class.getSimpleName();

    public BakingTask(BakingDatabase bakingDatabase, RemoteListener remoteListener) {
        mBakingDatabase = bakingDatabase;
        mRemoteListener = remoteListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mRemoteListener.preExecute();
    }

    @Override
    public Boolean doInBackground(URL... params) {
        URL url = params[0];
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }

                Log.d(TAG, result.toString());
                List<Recipe> recipes = JsonUtils.parseRecipesJson(result.toString());
                for (int i = 0; i < recipes.size(); i++) {
                    Recipe recipe = recipes.get(i);
                    final DatabaseRecipe databaseRecipe = new DatabaseRecipe(
                            recipe.getId(),
                            recipe.getName()
                    );
                    mBakingDatabase.bakingDao().insertRecipe(databaseRecipe);
                }

                mBakingDatabase.bakingDao().deleteIngredients();
                List<Ingredient> ingredients = JsonUtils.parseIngredientsJson(result.toString());
                for (int i = 0; i < ingredients.size(); i++) {
                    Ingredient ingredient = ingredients.get(i);
                    final DatabaseIngredient databaseIngredient = new DatabaseIngredient(
                            ingredient.getQuantity(),
                            ingredient.getMeasure(),
                            ingredient.getIngredient(),
                            ingredient.getRecipe_id()
                    );
                    mBakingDatabase.bakingDao().insertIngredient(databaseIngredient);
                }

                mBakingDatabase.bakingDao().deleteSteps();
                List<Step> steps = JsonUtils.parseStepsJson(result.toString());
                for (int i = 0; i < steps.size(); i++) {
                    Step step = steps.get(i);
                    final DatabaseStep databaseStep = new DatabaseStep(
                            step.getStep(),
                            step.getShortDescription(),
                            step.getDescription(),
                            step.getVideo_url(),
                            step.getThumbnail_url(),
                            step.getRecipe_id()
                    );
                    mBakingDatabase.bakingDao().insertStep(databaseStep);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean data) {
        mRemoteListener.postExecute(data);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mRemoteListener.postExecute(false);
    }
}
