package com.udacity.luisev96.baking.domain;

public class Ingredient {

    private Double quantity;
    private String measure;
    private String ingredient;
    private int recipe_id;

    public Ingredient(Double quantity, String measure, String ingredient, int recipe_id) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipe_id = recipe_id;
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
