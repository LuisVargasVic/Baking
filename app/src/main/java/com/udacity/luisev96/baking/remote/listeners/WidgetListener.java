package com.udacity.luisev96.baking.remote.listeners;

import com.udacity.luisev96.baking.domain.Recipe;

public interface WidgetListener {
    void postWidgetExecute(Recipe recipe);
}