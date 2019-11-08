package com.udacity.luisev96.baking.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BakingDao {

    @Query("SELECT * FROM DatabaseRecipe")
    LiveData<List<DatabaseRecipe>> getRecipes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(DatabaseRecipe recipe);

    @Query("SELECT * FROM DatabaseIngredient WHERE recipe_id = :recipeId")
    LiveData<List<DatabaseIngredient>> getIngredients(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredient(DatabaseIngredient ingredient);

    @Query("SELECT * FROM DatabaseStep WHERE recipe_id = :recipeId")
    LiveData<List<DatabaseStep>> getSteps(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStep(DatabaseStep review);

    @Query("DELETE FROM DatabaseIngredient")
    void deleteIngredients();

    @Query("DELETE FROM DatabaseStep")
    void deleteSteps();

    @Query("SELECT * FROM DatabaseStep WHERE recipe_id = :recipeId AND step = :step_id")
    LiveData<DatabaseStep> getStep(int recipeId, int step_id);
}