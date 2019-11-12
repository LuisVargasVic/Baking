package com.udacity.luisev96.baking.utils;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.data.BakingRepository;
import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.domain.Recipe;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = WidgetProvider.class.getSimpleName();
    static final String APP_WIDGET_ID = "app_widget_id";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe) {
        RemoteViews rv;
        rv = getRecyclerRemoteView(context, recipe);
        appWidgetManager.updateAppWidget(recipe.getApp_widget_id(), rv);
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the ListView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the ListView mode widget
     */
    private static RemoteViews getRecyclerRemoteView(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
        // Set the ListWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, ListWidgetService.class);
        intent.putExtra(APP_WIDGET_ID, recipe.getApp_widget_id());
        views.setTextViewText(R.id.tv_recipe_name, recipe.getName());
        views.setRemoteAdapter(R.id.list_view, intent);
        // Handle empty ingredients
        views.setEmptyView(R.id.list_view, R.id.empty_view);
        return views;
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            BakingDatabase database = BakingDatabase.getInstance(context);
            Log.d(TAG, "Actively retrieving the widget from the DataBase");
            BakingRepository repository = new BakingRepository(database);
            repository.getRecipe(appWidgetId).observeForever(new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        IngredientsService.startActionUpdateWidgets(context, recipe);
                    }
                }
            });
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            BakingDatabase database = BakingDatabase.getInstance(context);
            Log.d(TAG, "Actively removing widget from the DataBase");
            BakingRepository repository = new BakingRepository(database);
            repository.deleteWidget(appWidgetId);
        }
    }
}