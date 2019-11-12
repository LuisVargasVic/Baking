package com.udacity.luisev96.baking.utils;

import com.udacity.luisev96.baking.domain.Ingredient;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.domain.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_MEASURE = "measure";
    private static final String KEY_INGREDIENT = "ingredient";
    private static final String KEY_STEPS = "steps";
    private static final String KEY_SHORT_DESCRIPTION = "shortDescription";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VIDEO_URL = "videoURL";
    private static final String KEY_THUMBNAIL_URL = "thumbnailURL";

    public static List<Recipe> parseRecipesJson(String json) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray objects = new JSONArray(json);
            for (int i = 0; i < objects.length(); i++) {
                JSONObject recipe = objects.getJSONObject(i);
                int id = recipe.getInt(KEY_ID);
                String name = recipe.getString(KEY_NAME);
                recipes.add(new Recipe(id, name, 0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public static List<Ingredient> parseIngredientsJson(String json) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            JSONArray objects = new JSONArray(json);
            for (int i = 0; i < objects.length(); i++) {
                JSONObject recipe = objects.getJSONObject(i);
                int id = recipe.getInt(KEY_ID);
                JSONArray ingredientsJSON = recipe.getJSONArray(KEY_INGREDIENTS);
                for (int j = 0; j < ingredientsJSON.length(); j++) {
                    JSONObject ingredient = ingredientsJSON.getJSONObject(j);
                    Double quantity = ingredient.getDouble(KEY_QUANTITY);
                    String measure = ingredient.getString(KEY_MEASURE);
                    String ingredientString = ingredient.getString(KEY_INGREDIENT);
                    ingredients.add(new Ingredient(quantity, measure, ingredientString, id));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public static List<Step> parseStepsJson(String json) {
        List<Step> steps = new ArrayList<>();
        try {
            JSONArray objects = new JSONArray(json);
            for (int i = 0; i < objects.length(); i++) {
                JSONObject recipe = objects.getJSONObject(i);
                int id = recipe.getInt(KEY_ID);
                JSONArray stepsJSON = recipe.getJSONArray(KEY_STEPS);
                for (int j = 0; j < stepsJSON.length(); j++) {
                    JSONObject step = stepsJSON.getJSONObject(j);
                    int step_id = step.getInt(KEY_ID);
                    String shortDescription = step.getString(KEY_SHORT_DESCRIPTION);
                    String description = step.getString(KEY_DESCRIPTION);
                    String videoURL = step.getString(KEY_VIDEO_URL);
                    String thumbnailURL = step.getString(KEY_THUMBNAIL_URL);
                    steps.add(new Step(step_id, shortDescription, description, videoURL, thumbnailURL, id));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return steps;
    }

}
