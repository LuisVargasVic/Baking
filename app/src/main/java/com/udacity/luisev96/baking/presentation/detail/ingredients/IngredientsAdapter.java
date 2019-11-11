package com.udacity.luisev96.baking.presentation.detail.ingredients;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.IngredientItemBinding;
import com.udacity.luisev96.baking.domain.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ListViewHolder> {

    private List<Ingredient> mIngredientsList = new ArrayList<>();

    IngredientsAdapter() {

    }

    public void setIngredients(List<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IngredientItemBinding onCreateViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.ingredient_item, parent, false);
        return new ListViewHolder(onCreateViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(mIngredientsList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mIngredientsList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        IngredientItemBinding mIngredientItemBinding;
        Ingredient mIngredient;

        ListViewHolder(@NonNull IngredientItemBinding ingredientItemBinding) {
            super(ingredientItemBinding.getRoot());
            mIngredientItemBinding = ingredientItemBinding;
        }

        void bind(Ingredient ingredient) {
            mIngredient = ingredient;
            mIngredientItemBinding.tvName.setText(ingredient.getIngredient());
            mIngredientItemBinding.tvQuantity.setText(String.valueOf(ingredient.getQuantity()));
            mIngredientItemBinding.tvMeasure.setText(ingredient.getMeasure());
        }
    }
}
