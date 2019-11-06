package com.udacity.luisev96.baking.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.RecipeItemBinding;
import com.udacity.luisev96.baking.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecipesViewHolder> {

    private List<Recipe> mRecipesList = new ArrayList<>();
    private Context mContext;

    MainAdapter(Context context) {
        mContext = context;
    }

    public void setRecipes(List<Recipe> mRecipesList) {
        this.mRecipesList = mRecipesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeItemBinding onCreateViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recipe_item, parent, false);
        return new RecipesViewHolder(onCreateViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        holder.bind(mRecipesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipesList.size();
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeItemBinding mRecipeItemBinding;
        Recipe mRecipe;

        RecipesViewHolder(@NonNull RecipeItemBinding recipeItemBinding) {
            super(recipeItemBinding.getRoot());
            mRecipeItemBinding = recipeItemBinding;
            itemView.setOnClickListener(this);
        }

        void bind(Recipe recipe) {
            mRecipe = recipe;

            mRecipeItemBinding.tvName.setText(String.valueOf(recipe.getName()));
        }

        @Override
        public void onClick(View view) {
            /*Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(RECIPE, mRecipe);
            mContext.startActivity(intent);*/
        }
    }
}
