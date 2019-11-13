package com.udacity.luisev96.baking.domain;

import java.io.Serializable;

public class Recipe implements Serializable {

    private int id;
    private String name;
    private int app_widget_id;

    public Recipe(int id, String name, int app_widget_id) {
        this.id = id;
        this.name = name;
        this.app_widget_id = app_widget_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getApp_widget_id() {
        return app_widget_id;
    }

}
