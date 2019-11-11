package com.udacity.luisev96.baking.utils;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.udacity.luisev96.baking.R;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class WidgetProvider extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int recipeId, int appWidgetId) {
        // Get current width to decide on single plant vs garden grid view
        RemoteViews rv;
        rv = getRecyclerRemoteView(context, recipeId);
        appWidgetManager.updateAppWidget(appWidgetId, rv);

    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int recipeId, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, recipeId, appWidgetId);
        }
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the RecyclerView mode widget
     *
     * @param context The context
     * @param id
     * @return The RemoteViews for the RecyclerView mode widget
     */
    private static RemoteViews getRecyclerRemoteView(Context context, int id) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
        // Set the ListWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(RECIPE_ID, id);
        views.setRemoteAdapter(R.id.list_view, intent);
        // Handle empty ingredients
        views.setEmptyView(R.id.list_view, R.id.empty_view);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            IngredientsService.startActionUpdateWidgets(context, appWidgetId);
        }
    }

}