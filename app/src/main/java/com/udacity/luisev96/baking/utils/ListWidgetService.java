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

import java.util.List;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int recipe_id = intent.getIntExtra(RECIPE_ID, -1);
        return new ListRemoteViewsFactory(this.getApplicationContext(), recipe_id);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    private final int mRecipeId;
    private Context mContext;
    private List<Ingredient> mIngredients;

    ListRemoteViewsFactory(Context applicationContext, int recipe_id) {
        mContext = applicationContext;
        mRecipeId = recipe_id;
    }

    @Override
    public void onCreate() {
        BakingRepository repository;

        BakingDatabase database = BakingDatabase.getInstance(mContext);
        Log.d(TAG, "Actively retrieving the ingredients from the DataBase");
        repository = new BakingRepository(database);

        repository.getIngredients(mRecipeId).observeForever(new Observer<List<Ingredient>>() {

            @Override
            public void onChanged(List<Ingredient> ingredients) {
                mIngredients = ingredients;

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(mContext, WidgetProvider.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
                WidgetProvider.updateWidgets(mContext, appWidgetManager, mRecipeId, appWidgetIds);
            }
        });
    }

    //called on start and when notifyAppWidgetViewDataChanged is called
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
