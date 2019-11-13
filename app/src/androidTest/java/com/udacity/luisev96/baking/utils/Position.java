package com.udacity.luisev96.baking.utils;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

// I got help from user https://stackoverflow.com/users/986216/riwnodennyk in StackOverflow to check at RecyclerView position in a ViewHolder to match
// whatever I'm looking for in this case I used it to get at position 1 the text "Brownies"
// https://stackoverflow.com/a/34795431
public class Position {
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> viewMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                viewMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                assert viewHolder != null;
                return viewMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
