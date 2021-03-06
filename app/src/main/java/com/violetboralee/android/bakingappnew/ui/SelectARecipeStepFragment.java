package com.violetboralee.android.bakingappnew.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.violetboralee.android.bakingappnew.R;
import com.violetboralee.android.bakingappnew.model.Ingredient;
import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;
import com.violetboralee.android.bakingappnew.util.TextUtil;
import com.violetboralee.android.bakingappnew.widget.UpdateIngredientService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe";
    private static final String LOG_TAG = SelectARecipeStepFragment.class.getSimpleName();
    ArrayList<String> mRecipeIngredientForWidgets;
    private RecyclerView mIngredientsRecyclerView;
    private IngredientAdapter mIngredientAdapter;
    private TextView mIngredientIntroduction;
    private RecyclerView mStepsRecyclerView;
    private StepShortDescriptionAdapter mShortDescriptionAdapter;
    private int mRecipeId;
    // The callback is a method named onStepClickListener that contains information about
    // which step a user has clicked
    private onStepClickListener mCallback;

    public static SelectARecipeStepFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_RECIPE_ID, recipeId);

        SelectARecipeStepFragment fragment = new SelectARecipeStepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * called when a fragment is attached to an activity
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(LOG_TAG, "--> onAttach");

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (onStepClickListener) context;
            // Activity is a subclass of context, so onAttach passes a Context as a parameter
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe_step, container, false);

        Log.i(LOG_TAG, "--> onCreateView");

        mRecipeId = getArguments().getInt(ARG_RECIPE_ID);

        mIngredientsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_ingredients);
        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mIngredientIntroduction = (TextView) view.findViewById(R.id.ingredient_introdiction);

        mStepsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_steps);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI(mRecipeId);

        mRecipeIngredientForWidgets = new ArrayList<>();

        updateWidget(mRecipeId);

        return view;
    }

    private void updateWidget(int recipeId) {

        RecipeLab recipeLab = RecipeLab.get(getActivity());
        List<Ingredient> ingredients = recipeLab.getIngredients(recipeId);

        for (Ingredient ingredient : ingredients) {
            mRecipeIngredientForWidgets.add(
                    TextUtil.capitalizeWords(ingredient.getIngredient())
                            + " (" + TextUtil.removeTrailingZero(Double.toString(ingredient.getQuantity())) + " " +
                            ingredient.getMeasure() + ")"
            );
        }

        // update the widget
        UpdateIngredientService.startIngredientUpdateService(getContext(), mRecipeIngredientForWidgets);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "--> onStart");

        updateUI(mRecipeId);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "--> onResume");

        updateUI(mRecipeId);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(LOG_TAG, "--> onDetach");

        mCallback = null;  // setting the variable to null because
        // afterward I cannot access the activity or count on the activity continuing to exist
    }

    private void updateUI(int recipeId) {

        Log.i(LOG_TAG, "--> updateUI");


        RecipeLab recipeLab = RecipeLab.get(getActivity());
        String foodName = recipeLab.getRecipe(recipeId).getName();

        Resources res = getResources();
        String ingredientIntroduction = String.format(res.getString(R.string.ingredient_introduction), foodName);

        List<Ingredient> ingredients = recipeLab.getIngredients(recipeId);
        List<Step> steps = recipeLab.getSteps(recipeId);

        if (mShortDescriptionAdapter == null) {
            mShortDescriptionAdapter = new StepShortDescriptionAdapter(steps);
            mStepsRecyclerView.setAdapter(mShortDescriptionAdapter);
        } else {
            mShortDescriptionAdapter.notifyDataSetChanged();
        }

        if (mIngredientAdapter == null) {
            mIngredientIntroduction.setText(ingredientIntroduction);
            mIngredientAdapter = new IngredientAdapter(ingredients);
            mIngredientsRecyclerView.setAdapter(mIngredientAdapter);
        } else {
            mIngredientAdapter.notifyDataSetChanged();
            mIngredientIntroduction.setText(foodName);

        }
    }

    /**
     * Delegating functionality back to the hosting activity
     * Required interface for hosting activities
     */
    public interface onStepClickListener {
        // defines work that the fragment needs done by its hosting activity,
        // SelectARecipeStepActivity
        void onStepSelected(int recipeId, Step step);
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

        private TextView mStepNumber;
        private TextView mShortDescriptionTextView;
        private Step mStep;
        private int mStepId;
        private int mCurrentIndex;

        public ShortDescriptionHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mStepNumber = (TextView) itemView.findViewById(R.id.step_number);
            mShortDescriptionTextView =
                    (TextView) itemView.findViewById(R.id.list_item_short_description_text_view);
        }

        public void bindStep(Step step) {
            mStep = step;
            mStepId = step.getId();
            String stepIdString = String.valueOf(mStepId);
            String shortDescription = step.getShortDescription();

            RecipeLab recipeLab = RecipeLab.get(getActivity());
            mRecipeId = getArguments().getInt(ARG_RECIPE_ID);

            mCurrentIndex = recipeLab.getStepsCurrentIndex(mRecipeId, mStep);

            mStepNumber.setText(stepIdString);
            mShortDescriptionTextView.setText(shortDescription);
        }


        @Override
        public void onClick(View v) {
            mCallback.onStepSelected(mRecipeId, mStep);
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
