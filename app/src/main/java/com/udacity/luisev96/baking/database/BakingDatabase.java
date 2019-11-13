package com.udacity.luisev96.baking.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        DatabaseRecipe.class,
        DatabaseIngredient.class,
        DatabaseStep.class,
        DatabaseWidget.class
}, version = 1, exportSchema = false)
public abstract class BakingDatabase extends RoomDatabase {

    private static final String LOG_TAG = BakingDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes";
    private static BakingDatabase sInstance;

    public static BakingDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BakingDatabase.class, BakingDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract BakingDao bakingDao();

}
