package com.udacity.luisev96.baking.presentation.detail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.ActivityDetailBinding;
import com.udacity.luisev96.baking.presentation.detail.ingredients.IngredientsFragment;
import com.udacity.luisev96.baking.presentation.detail.steps.StepFragment;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.INGREDIENTS;
import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.POSITION;
import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;
import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.TOTAL;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding activityDetailBinding;
    boolean ingredients;
    int position;
    int recipeId;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        if (savedInstanceState != null) {
            ingredients = savedInstanceState.getBoolean(INGREDIENTS);
            position = savedInstanceState.getInt(POSITION);
            recipeId = savedInstanceState.getInt(RECIPE_ID);
            total = savedInstanceState.getInt(TOTAL);
        } else if (getIntent() != null) {
            if (getIntent().hasExtra(INGREDIENTS)) ingredients = getIntent().getBooleanExtra(INGREDIENTS, true);
            if (getIntent().hasExtra(POSITION)) position = getIntent().getIntExtra(POSITION, 0);
            if (getIntent().hasExtra(RECIPE_ID)) recipeId = getIntent().getIntExtra(RECIPE_ID, 0);
            if (getIntent().hasExtra(TOTAL)) total = getIntent().getIntExtra(TOTAL, 0);
        }

        navigate();

        activityDetailBinding.bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityDetailBinding.bNext.setEnabled(false);
                position++;
                if (position == 0) ingredients = false;
                navigate();
            }
        });

        activityDetailBinding.bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityDetailBinding.bBack.setEnabled(false);
                position--;
                if (position == -1) ingredients = true;
                navigate();
            }
        });

    }

    private void navigate() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == - 1) {
            activityDetailBinding.bBack.setVisibility(View.INVISIBLE);
        } else if (position == total - 2) {
            activityDetailBinding.bNext.setVisibility(View.INVISIBLE);
        } else {
            activityDetailBinding.bBack.setVisibility(View.VISIBLE);
            activityDetailBinding.bNext.setVisibility(View.VISIBLE);
        }
        if (ingredients) {
            setTitle(R.string.ingredients);
            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(RECIPE_ID, recipeId);
            ingredientsFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, ingredientsFragment)
                    .commit();
        } else {
            StepFragment stepFragment = new StepFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(RECIPE_ID, recipeId);
            bundle.putInt(POSITION, position);
            stepFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, stepFragment)
                    .commit();
        }

        activityDetailBinding.bBack.setEnabled(true);
        activityDetailBinding.bNext.setEnabled(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(INGREDIENTS, ingredients);
        outState.putInt(POSITION, position);
        outState.putInt(RECIPE_ID, recipeId);
        outState.putInt(TOTAL, total);
    }
}
