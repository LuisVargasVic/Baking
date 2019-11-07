package com.udacity.luisev96.baking.presentation.detail.list;

import android.content.Context;
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
import com.udacity.luisev96.baking.databinding.FragmentListBinding;
import com.udacity.luisev96.baking.domain.Step;

import java.util.List;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;

// This fragment displays the list of steps and ingredients without detail
public class ListFragment extends Fragment {

    private static final String TAG = ListFragment.class.getSimpleName();
    // Define a new interface OnItemClickListener that triggers a callback in the host activity
    OnItemClickListener mCallback;
    private FragmentListBinding fragmentListBinding;
    private ListViewModel viewModel;
    private ListAdapter mAdapter;
    private int recipeId;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnItemClickListener {
        void onItemSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    // Mandatory empty constructor
    public ListFragment() {
    }

    // Inflates the RecyclerView of steps and ingredients
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);

        // Create the adapter
        mAdapter = new ListAdapter(mCallback);

        // Set the adapter on the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentListBinding.rvDescriptions.setLayoutManager(layoutManager);
        fragmentListBinding.rvDescriptions.setHasFixedSize(true);
        fragmentListBinding.rvDescriptions.setAdapter(mAdapter);

        // Return the root view
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        recipeId = getArguments().getInt(RECIPE_ID);

        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);
        viewModel.init(recipeId);

        viewModel.getSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable List<Step> steps) {
                Log.d(TAG, "Updating list of steps from LiveData in ViewModel");
                mAdapter.setSteps(steps);
            }
        });
    }
}
