package com.udacity.luisev96.baking.presentation.detail.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.RecipeItemBinding;
import com.udacity.luisev96.baking.domain.Step;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<Step> mStepsList = new ArrayList<>();
    private ListFragment.OnItemClickListener mCallback;

    ListAdapter(ListFragment.OnItemClickListener callback) {
        mCallback = callback;
    }

    public void setSteps(List<Step> mStepsList) {
        this.mStepsList = mStepsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeItemBinding onCreateViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recipe_item, parent, false);
        return new ListViewHolder(onCreateViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (position == 0) {
            holder.bind(position);
        } else {
            holder.bind(mStepsList.get(position - 1), position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mStepsList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeItemBinding mRecipeItemBinding;
        Step mStep;
        int mPosition = 0;

        ListViewHolder(@NonNull RecipeItemBinding recipeItemBinding) {
            super(recipeItemBinding.getRoot());
            mRecipeItemBinding = recipeItemBinding;
            mRecipeItemBinding.mcvRecipes.setOnClickListener(this);
        }

        void bind(Step step, int position) {
            mStep = step;

            mRecipeItemBinding.tvName.setText(String.valueOf(step.getShortDescription()));
            mPosition = position;
        }

        void bind(int position) {
            mRecipeItemBinding.tvName.setText(R.string.ingredients);
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mCallback.onItemSelected(mPosition);
        }
    }
}