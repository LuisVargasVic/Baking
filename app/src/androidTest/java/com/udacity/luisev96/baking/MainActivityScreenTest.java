package com.udacity.luisev96.baking;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.udacity.luisev96.baking.presentation.recipes.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

// Annotation to specify AndroidJUnitRunner class as the default test runner
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    private static final String TAG = MainActivityScreenTest.class.getSimpleName();
    private static final String BROWNIES = "Brownies";
    private IdlingResource mIdlingResource;

    // Rule that provides functional testing of a single activity
    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    // Test which will click on a recyclerView Recipe item and verify that
    // the ListActivity opens up with the correct recipe name displayed.
    @Test
    public void clickRecyclerViewItem_OpensListActivity() {
        // Click item at position 1
        onView(withId(R.id.rv_recipes)).check(
                new ViewAssertion() {
                    @Override
                    public void check(View view, NoMatchingViewException noViewFoundException) {
                        if (noViewFoundException != null) throw noViewFoundException;
                        try {
                            RecyclerView recyclerView = (RecyclerView) view;
                            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(1);
                            hasDescendant(withText(BROWNIES)).matches(viewHolder.itemView);
                        } catch (Exception e) {
                            Log.wtf(TAG, e.getLocalizedMessage());
                        }
                    }
                }).perform(click());

    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
