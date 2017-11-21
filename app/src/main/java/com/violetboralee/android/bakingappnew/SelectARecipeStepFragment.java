package com.violetboralee.android.bakingappnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.violetboralee.android.bakingappnew.pojo.Recipe;
import com.violetboralee.android.bakingappnew.pojo.Step;

import java.util.List;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepFragment extends Fragment implements
        LoaderManager.LoaderCallbacks {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final int RECIPE_FETCH_LOADER_ID = 20;

    private RecyclerView mStepsRecyclerView;
    private ShortDescriptionAdapter mAdapter;

    private List<Step> mSteps;
//    private Step mStep;

    public static SelectARecipeStepFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);

        SelectARecipeStepFragment fragment = new SelectARecipeStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int recipeId = getArguments().getInt(ARG_RECIPE_ID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe_step, container, false);

        mStepsRecyclerView = (RecyclerView) view.findViewById(R.id.steps_recycler_view);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        int recipeId = getActivity().getIntent().
                getIntExtra(SelectARecipeStepActivity.EXTRA_RECIPE_ID, 1);

        mSteps = RecipeLab.get(getActivity()).getSteps(recipeId);

        updateUI(recipeId);

        return view;
    }

    private void updateUI(int recipeId) {
        RecipeLab recipeLab = RecipeLab.get(getActivity());
        mSteps = recipeLab.getSteps(recipeId);

        mAdapter = new ShortDescriptionAdapter(mSteps);
        mStepsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == RECIPE_FETCH_LOADER_ID) {


        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    private class ShortDescriptionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView mShortDescriptionTextView;
        private Step mStep;

        public ShortDescriptionHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mShortDescriptionTextView =
                    (TextView) itemView.findViewById(R.id.list_item_short_description_text_view);
        }

        public void bindStep(Step step) {
            mStep = step;
            mShortDescriptionTextView.setText(step.getId() + ". " + step.getShortDescription());
        }

        public void setRecipeData(List<Recipe> recipeData) {

        }

        @Override
        public void onClick(View v) {
//            Intent intent = ViewRecipeStepActivity.newIntent(getActivity(), mStep.getId());
            Intent intent = new Intent(getActivity(), ViewRecipeStepActivity.class);
            startActivity(intent);
        }
    }

    private class ShortDescriptionAdapter extends RecyclerView.Adapter<ShortDescriptionHolder> {

        public ShortDescriptionAdapter(List<Step> steps) {
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
