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

import com.violetboralee.android.bakingappnew.model.Recipe;
import com.violetboralee.android.bakingappnew.model.RecipeLab;

import java.util.List;

/**
 * Created by bora on 19/11/2017.
 */

public class SelectARecipeFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe, container, false);

        mRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipes_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        RecipeLab recipeLab = RecipeLab.get(getActivity());
        List<Recipe> recipes = recipeLab.getRecipes();

        if (mAdapter == null) {
            mAdapter = new RecipeAdapter(recipes);
            mRecipeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }




    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mRecipeNameTextView;
        private Recipe mRecipe;

        public RecipeHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            mRecipeNameTextView =
                    (TextView) itemView.findViewById(R.id.list_item_recipe_name_text_view);
        }

        public void bindRecipe(Recipe recipe) {
            mRecipe = recipe;
            mRecipeNameTextView.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = SelectARecipeStepActivity.newIntent(getActivity(), mRecipe.getId());
            startActivity(intent);
        }

    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        final private List<Recipe> mRecipes;

        public RecipeAdapter(List<Recipe> recipes) {
            mRecipes = recipes;
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.list_item_recipe, parent, false);

            return new RecipeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            Recipe recipe = mRecipes.get(position);
            holder.bindRecipe(recipe);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }

    }

}
