package com.violetboralee.android.bakingappnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.violetboralee.android.bakingappnew.model.Ingredient;
import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;

import java.util.List;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe";

    private RecyclerView mStepsRecyclerView;
    private StepShortDescriptionAdapter mAdapter;

    private int stepId;

    private int mRecipeId;


    public static SelectARecipeStepFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);

        SelectARecipeStepFragment fragment = new SelectARecipeStepFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe_step, container, false);

        mRecipeId = getArguments().getInt(ARG_RECIPE_ID);

        mStepsRecyclerView = (RecyclerView) view.findViewById(R.id.steps_recycler_view);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI(mRecipeId);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mRecipeId);
    }

    private void updateUI(int recipeId) {
        RecipeLab recipeLab = RecipeLab.get(getActivity());
        List<Step> steps = recipeLab.getSteps(recipeId);
        List<Ingredient> ingredients = recipeLab.getIngredients(recipeId);

        if (mAdapter == null) {
            mAdapter = new StepShortDescriptionAdapter(steps);
            mStepsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    private class ShortDescriptionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView mShortDescriptionTextView;
        private Step mStep;
        private int stepId;

        public ShortDescriptionHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mShortDescriptionTextView =
                    (TextView) itemView.findViewById(R.id.list_item_short_description_text_view);
        }

        public void bindStep(Step step) {
            mStep = step;
            stepId = step.getId();
            mShortDescriptionTextView.setText(step.getId() + ". " + step.getShortDescription());
        }


        @Override
        public void onClick(View v) {
            Intent intent = ViewRecipeStepActivity.newIntent(getActivity(), mRecipeId, stepId);
            startActivity(intent);
        }
    }

    private class StepShortDescriptionAdapter extends RecyclerView.Adapter<ShortDescriptionHolder> {
        final private List<Step> mSteps;

        public StepShortDescriptionAdapter(List<Step> steps) {
            mSteps = steps;
        }

        @Override
        public ShortDescriptionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_step, parent, false);

            return new ShortDescriptionHolder(view);
        }

        @Override
        public void onBindViewHolder(ShortDescriptionHolder holder, int position) {
            Step step = mSteps.get(position);
            holder.bindStep(step);
        }

        @Override
        public int getItemCount() {
            return mSteps.size();
        }
    }
}
