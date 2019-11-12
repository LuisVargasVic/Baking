package com.udacity.luisev96.baking.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.Observer;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.data.BakingRepository;
import com.udacity.luisev96.baking.database.BakingDatabase;
import com.udacity.luisev96.baking.domain.Ingredient;
import com.udacity.luisev96.baking.domain.Recipe;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = Integer.valueOf(intent.getData().getSchemeSpecificPart());
        return new ListRemoteViewsFactory(this.getApplicationContext(), appWidgetId);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    private int mAppWidgetId;
    private Context mContext;
    private List<Ingredient> mIngredients;

    ListRemoteViewsFactory(Context applicationContext, int appWidgetId) {
        mContext = applicationContext;
        mAppWidgetId = appWidgetId;
    }

    @Override
    public void onCreate() {
        final BakingRepository repository;

        BakingDatabase database = BakingDatabase.getInstance(mContext);
        Log.d(TAG, "Actively retrieving the ingredients from the DataBase");
        repository = new BakingRepository(database);

        repository.getRecipe(mAppWidgetId).observeForever(new Observer<Recipe>() {
            @Override
            public void onChanged(final Recipe recipe) {
                if (recipe != null) {
                    repository.getIngredients(recipe.getId()).observeForever(new Observer<List<Ingredient>>() {

                        @Override
                        public void onChanged(List<Ingredient> ingredients) {
                            mIngredients = ingredients;

                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, WidgetProvider.class));
                            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
                            WidgetProvider.updateWidgets(mContext, appWidgetManager, appWidgetIds, recipe);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null || mIngredients.isEmpty()) return null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.tv_name, mIngredients.get(position).getIngredient());
        views.setTextViewText(R.id.tv_quantity, String.valueOf(mIngredients.get(position).getQuantity()));
        views.setTextViewText(R.id.tv_measure, mIngredients.get(position).getMeasure());
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the ListView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
