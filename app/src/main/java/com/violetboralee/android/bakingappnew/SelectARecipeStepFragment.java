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
import com.violetboralee.android.bakingappnew.util.TextUtil;

import java.util.List;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe";

    private RecyclerView mIngredientsRecyclerView;
    private IngredientAdapter mIngredientAdapter;

    private RecyclerView mStepsRecyclerView;
    private StepShortDescriptionAdapter mShortDescriptionAdapter;

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

        mIngredientsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_ingredients);
        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mStepsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_steps);
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

        if (mShortDescriptionAdapter == null) {
            mShortDescriptionAdapter = new StepShortDescriptionAdapter(steps);
            mStepsRecyclerView.setAdapter(mShortDescriptionAdapter);
        } else {
            mShortDescriptionAdapter.notifyDataSetChanged();
        }

        if (mIngredientAdapter == null) {
            mIngredientAdapter = new IngredientAdapter(ingredients);
            mIngredientsRecyclerView.setAdapter(mIngredientAdapter);
        } else {
            mIngredientAdapter.notifyDataSetChanged();
        }
    }


    private class IngredientHolder extends RecyclerView.ViewHolder {
        private Ingredient mIngredient;
        private TextView mIngredientTextView;

        public IngredientHolder(View itemView) {
            super(itemView);
            mIngredientTextView = (TextView) itemView.findViewById(R.id.tv_list_item_ingredient);
        }

        public void bindIngredient(Ingredient ingredient) {
            mIngredient = ingredient;

            String ingredientDetail = mIngredient.getIngredient();
            String trimmedIngredientDetail = TextUtil.capitalizeWords(ingredientDetail);

            Double quantity = mIngredient.getQuantity();
            String quantityString = Double.toString(quantity);
            String trimmedQuantityString = TextUtil.removeTrailingZero(quantityString);

            String measure = mIngredient.getMeasure();

            String combinedIngredient = String.format(getResources().getString(R.string.ingredient),
                    trimmedIngredientDetail,
                    trimmedQuantityString,
                    measure
            );

            mIngredientTextView.setText(combinedIngredient);
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {
        final private List<Ingredient> mIngredients;

        public IngredientAdapter(List<Ingredient> ingredients) {
            mIngredients = ingredients;
        }

        @Override
        public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_ingredients, parent, false);

            return new IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(IngredientHolder holder, int position) {
            Ingredient ingredient = mIngredients.get(position);
            holder.bindIngredient(ingredient);

        }

        @Override
        public int getItemCount() {
            return mIngredients.size();
        }
    }



    private class ShortDescriptionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mShortDescriptionTextView;
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
            Intent intent = ViewRecipeStepPagerActivity.newIntent(getActivity(), mRecipeId, stepId);
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
