package com.udacity.luisev96.baking.presentation.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.ActivityMasterDetailBinding;
import com.udacity.luisev96.baking.domain.Recipe;
import com.udacity.luisev96.baking.presentation.detail.ingredients.IngredientsFragment;
import com.udacity.luisev96.baking.presentation.detail.list.ListFragment;
import com.udacity.luisev96.baking.presentation.detail.steps.StepFragment;

public class MasterDetailActivity extends AppCompatActivity implements ListFragment.OnItemClickListener {

    public static final String RECIPE_ID = "recipe";
    public static final String INGREDIENTS = "ingredients";
    public static final String POSITION = "position";
    public static final String TOTAL = "total";
    private FragmentManager fragmentManager;
    Recipe recipe;

    // Variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMasterDetailBinding activityMasterDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_master_detail);

        if (savedInstanceState != null) recipe = (Recipe) savedInstanceState.getSerializable(RECIPE_ID);
        else if (getIntent() != null)
            if (getIntent().hasExtra(RECIPE_ID)) recipe = (Recipe) getIntent().getSerializableExtra(RECIPE_ID);

        assert recipe != null;
        setTitle(recipe.getName());
        fragmentManager = getSupportFragmentManager();

        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID, recipe.getId());
        listFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, listFragment)
                .commit();

        twoPane = activityMasterDetailBinding.detailContainer != null;
    }

    @Override
    public void onItemSelected(int position, int total) {
        Boolean ingredients = position == -1;
        if (twoPane) {
            if (ingredients) {
                setTitle(R.string.ingredients);
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(RECIPE_ID, recipe.getId());
                ingredientsFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, ingredientsFragment)
                        .commit();
            } else {
                StepFragment stepFragment = new StepFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(RECIPE_ID, recipe.getId());
                bundle.putInt(POSITION, position);
                stepFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, stepFragment)
                        .commit();
            }
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(RECIPE_ID, recipe.getId());
            intent.putExtra(INGREDIENTS, ingredients);
            intent.putExtra(POSITION, position);
            intent.putExtra(TOTAL, total);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPE_ID, recipe);
    }
}
