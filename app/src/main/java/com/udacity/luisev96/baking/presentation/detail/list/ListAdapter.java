package com.udacity.luisev96.baking.presentation.detail.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.ListItemBinding;
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
        ListItemBinding onCreateViewHolder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
        return new ListViewHolder(onCreateViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (position == 0) {
            holder.bind(position);
        } else {
            holder.bind(mStepsList.get(position - 1));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mStepsList.size() + 1;
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListItemBinding mListItemBinding;
        Step mStep;
        int mPosition = 0;

        ListViewHolder(@NonNull ListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            mListItemBinding = listItemBinding;
            mListItemBinding.mcvList.setOnClickListener(this);
        }

        void bind(Step step) {
            mStep = step;
            mListItemBinding.tvName.setText(step.getShortDescription());
            mPosition = step.getStep();
        }

        void bind(int position) {
            mListItemBinding.tvName.setText(R.string.ingredients);
            mPosition = position - 1;
        }

        @Override
        public void onClick(View v) {
            mCallback.onItemSelected(mPosition, getItemCount());
        }
    }
}