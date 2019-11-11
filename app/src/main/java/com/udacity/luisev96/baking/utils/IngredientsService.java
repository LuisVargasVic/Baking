package com.udacity.luisev96.baking.utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.udacity.luisev96.baking.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class IngredientsService extends IntentService {

    public static final String ACTION_UPDATE_WIDGETS = "com.udacity.luisev96.baking.widget";
    public static final String WIDGET_ID = "widget_id";

    public IngredientsService() {
        super("IngredientsService");
    }

    /**
     * Starts this service to perform UpdateIngredientsWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateWidgets(Context context, int appWidgetId) {
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(ACTION_UPDATE_WIDGETS);
        intent.putExtra(WIDGET_ID, appWidgetId);
        context.startService(intent);
    }

    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGETS.equals(action)) {
                int appWidgetId = intent.getIntExtra(WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

                if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
                    return;
                handleActionUpdateRecipeWidgets();
            }
        }
    }

    /**
     * Handle action UpdateIngredientsWidgets in the provided background thread
     */
    private void handleActionUpdateRecipeWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, WidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        WidgetProvider.updateWidgets(this, appWidgetManager, 1, appWidgetIds);
    }
}
