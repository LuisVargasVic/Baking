package com.udacity.luisev96.baking.domain;

import java.io.Serializable;

public class Recipe implements Serializable {

    private int id;
    private String name;

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
