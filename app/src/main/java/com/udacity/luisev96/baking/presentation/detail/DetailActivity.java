package com.udacity.luisev96.baking.presentation.detail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.presentation.detail.ingredients.IngredientsFragment;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;

public class DetailActivity extends AppCompatActivity {

    int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState != null) recipeId = savedInstanceState.getInt(RECIPE_ID);
        else if (getIntent() != null)
            if (getIntent().hasExtra(RECIPE_ID)) recipeId = getIntent().getIntExtra(RECIPE_ID, 0);

        FragmentManager fragmentManager = getSupportFragmentManager();

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID, recipeId);
        ingredientsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.detail_container, ingredientsFragment)
                .commit();
    }
}
