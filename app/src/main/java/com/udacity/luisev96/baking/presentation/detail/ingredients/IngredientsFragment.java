package com.udacity.luisev96.baking.presentation.detail.ingredients;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.FragmentIngredientsBinding;
import com.udacity.luisev96.baking.domain.Ingredient;

import java.util.List;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    private static final String TAG = IngredientsFragment.class.getSimpleName();
    private FragmentIngredientsBinding fragmentIngredientsBinding;
    private IngredientsViewModel viewModel;
    private IngredientsAdapter mAdapter;
    private int recipeId;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentIngredientsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);

        // Create the adapter
        mAdapter = new IngredientsAdapter();

        // Set the adapter on the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentIngredientsBinding.rvIngredients.setLayoutManager(layoutManager);
        fragmentIngredientsBinding.rvIngredients.setHasFixedSize(true);
        fragmentIngredientsBinding.rvIngredients.setAdapter(mAdapter);

        // Return the root view
        return fragmentIngredientsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        recipeId = getArguments().getInt(RECIPE_ID);

        viewModel = ViewModelProviders.of(this).get(IngredientsViewModel.class);
        viewModel.init(recipeId);

        viewModel.getIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                Log.d(TAG, "Updating list of steps from LiveData in ViewModel");
                mAdapter.setIngredients(ingredients);
            }
        });
    }
}
