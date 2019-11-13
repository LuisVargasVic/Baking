package com.udacity.luisev96.baking.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DatabaseIngredient {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Double quantity;
    private String measure;
    private String ingredient;
    private int recipe_id;

    public DatabaseIngredient(Double quantity, String measure, String ingredient, int recipe_id) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipe_id = recipe_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

}
