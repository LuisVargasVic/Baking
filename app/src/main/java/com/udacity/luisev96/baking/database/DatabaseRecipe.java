package com.udacity.luisev96.baking.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DatabaseRecipe {

    @PrimaryKey
    private int id;
    private String name;

    public DatabaseRecipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
