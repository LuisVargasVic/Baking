package com.udacity.luisev96.baking.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DatabaseStep {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private int step;
    private String shortDescription;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private int recipe_id;

    public DatabaseStep(int step, String shortDescription, String description, String video_url, String thumbnail_url, int recipe_id) {
        this.step = step;
        this.shortDescription = shortDescription;
        this.description = description;
        this.video_url = video_url;
        this.thumbnail_url = thumbnail_url;
        this.recipe_id = recipe_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

}
