package com.udacity.luisev96.baking.domain;

public class Step {

    private int step;
    private String shortDescription;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private int recipe_id;

    public Step(int step, String shortDescription, String description, String video_url, String thumbnail_url, int recipe_id) {
        this.step = step;
        this.shortDescription = shortDescription;
        this.description = description;
        this.video_url = video_url;
        this.thumbnail_url = thumbnail_url;
        this.recipe_id = recipe_id;
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
