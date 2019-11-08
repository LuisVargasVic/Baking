package com.udacity.luisev96.baking.presentation.detail.steps;


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

import com.udacity.luisev96.baking.R;
import com.udacity.luisev96.baking.databinding.FragmentStepBinding;
import com.udacity.luisev96.baking.domain.Step;

import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.POSITION;
import static com.udacity.luisev96.baking.presentation.detail.MasterDetailActivity.RECIPE_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    private static final String TAG = StepFragment.class.getSimpleName();
    private FragmentStepBinding fragmentStepBinding;

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentStepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_step, container, false);
        return fragmentStepBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        int recipeId = getArguments().getInt(RECIPE_ID);
        int stepId = getArguments().getInt(POSITION);
        Log.wtf(TAG, "Step " + stepId);

        StepViewModel viewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        viewModel.init(recipeId, stepId);

        viewModel.getStep().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                Log.d(TAG, "Updating step from LiveData in ViewModel");
                assert step != null;
                try {
                    getActivity().setTitle(step.getShortDescription());
                    fragmentStepBinding.tvDescription.setText(step.getDescription());
                } catch (NullPointerException exception) {
                    fragmentStepBinding.tvDescription.setText(R.string.no_data_step);
                    Log.wtf(TAG, exception.getLocalizedMessage());
                }
            }
        });
    }
}
