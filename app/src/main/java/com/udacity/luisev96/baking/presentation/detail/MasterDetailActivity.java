package com.udacity.luisev96.baking.presentation.detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.ActivityMasterDetailBinding;
import com.udacity.luisev96.baking.presentation.detail.list.ListFragment;

public class MasterDetailActivity extends AppCompatActivity implements ListFragment.OnItemClickListener {

    public static final String RECIPE_ID = "recipe";
    private static final String INGREDIENTS = "ingredients";
    private ActivityMasterDetailBinding activityMasterDetailBinding;
    int recipeId;

    // Variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMasterDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_master_detail);

        if (savedInstanceState != null) recipeId = savedInstanceState.getInt(RECIPE_ID);
        else if (getIntent() != null)
            if (getIntent().hasExtra(RECIPE_ID)) recipeId = getIntent().getIntExtra(RECIPE_ID, 0);

        FragmentManager fragmentManager = getSupportFragmentManager();

        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_ID, recipeId);
        listFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.container, listFragment)
                .commit();

        twoPane = activityMasterDetailBinding.detailContainer != null;
    }

    @Override
    public void onItemSelected(int position) {
        Boolean ingredients = position == 0;
        if (twoPane) {

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(RECIPE_ID, recipeId);
            intent.putExtra(INGREDIENTS, ingredients);
            startActivity(intent);
        }
    }
}
