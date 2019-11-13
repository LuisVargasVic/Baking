package com.udacity.luisev96.baking.utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.domain.Recipe;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class IngredientsService extends IntentService {

    private static final String ACTION_UPDATE_WIDGETS = "com.udacity.luisev96.baking.action.widget";
    private static final String RECIPE = "recipe";

    public IngredientsService() {
        super("IngredientsService");
    }

    /**
     * Starts this service to perform UpdateIngredientsWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateWidgets(Context context, Recipe recipe) {
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        intent.putExtra(RECIPE, recipe);
        context.startService(intent);
    }

    /**
     * @param intent intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                Recipe recipe = (Recipe) intent.getSerializableExtra(RECIPE);
                handleActionUpdateRecipeWidgets(recipe);
            }
        }
    }

    /**
     * Handle action UpdateIngredientsWidgets in the provided background thread
     */
    private void handleActionUpdateRecipeWidgets(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(IngredientsService.this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(IngredientsService.this, WidgetProvider.class));
        //Trigger data update to handle the ListView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        //Now update widget
        WidgetProvider.updateWidget(IngredientsService.this, appWidgetManager, recipe);
    }
}