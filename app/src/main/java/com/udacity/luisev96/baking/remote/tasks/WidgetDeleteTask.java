package com.udacity.luisev96.baking.remote.tasks;

import android.os.AsyncTask;

import com.udacity.luisev96.baking.database.BakingDatabase;

public class WidgetDeleteTask extends AsyncTask<Integer, Void, Boolean> {

    private BakingDatabase mBakingDatabase;

    public WidgetDeleteTask(BakingDatabase bakingDatabase) {
        mBakingDatabase = bakingDatabase;
    }

    @Override
    public Boolean doInBackground(Integer... params) {
        try {
            mBakingDatabase.bakingDao().deleteWidget(params[0]);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
