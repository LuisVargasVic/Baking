package com.udacity.luisev96.baking.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DatabaseWidget {

    @PrimaryKey
    private int id;
    private int recipe_id;
    private String name;

    public DatabaseWidget(int id, int recipe_id, String name) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public String getName() {
        return name;
    }

}
