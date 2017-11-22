package com.violetboralee.android.bakingappnew;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.violetboralee.android.bakingappnew.pojo.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bora on 19/11/2017.
 */

public class SelectARecipeFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final String FETCHED_RAW_JSON = "recipes";

    private static final int RECIPE_FETCH_LOADER_ID = 20;
    private List<Recipe> mRecipes;

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private RecipeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList
                (FETCHED_RAW_JSON, (ArrayList<? extends Parcelable>) mRecipes);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recipes_recycler_view);
        mErrorMessageDisplay = (TextView) view.findViewById(R.id.tv_error_message_display);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(FETCHED_RAW_JSON);
        }

        fetchJsonData();

        return view;

    }

    private void fetchJsonData() {

        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<List<Recipe>> fetchJsonLoader = loaderManager.getLoader(RECIPE_FETCH_LOADER_ID);
        if (fetchJsonLoader == null) {
            loaderManager.initLoader(RECIPE_FETCH_LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(RECIPE_FETCH_LOADER_ID, null, this);
        }
        mAdapter = new RecipeAdapter(mRecipes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<List<Recipe>>(getActivity()) {

            @Override
            protected void onStartLoading() {

                if (mRecipes != null) {
                    deliverResult(mRecipes);
                } else {
                    forceLoad();
                }
            }

            @Override
            public List<Recipe> loadInBackground() {
                String recipeJson = null;

                try {
                    InputStream is = getActivity().getAssets().open("baking.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    recipeJson = new String(buffer, "UTF-8");
                    Gson gson = new Gson();
                    Type recipeListType = new TypeToken<ArrayList<Recipe>>() {
                    }.getType();

                    mRecipes = gson.fromJson(recipeJson, recipeListType);

                    return mRecipes;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(List<Recipe> data) {
                mRecipes = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mAdapter.setRecipeData(data);

        if (data == null) {
            showErrorMessage();
        } else {
            showRecipeList();
        }

    }

    private void showRecipeList() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

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

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), SelectARecipeStepActivity.class);
            startActivity(intent);
        }

        public void bindRecipe(Recipe recipe) {
            mRecipe = recipe;
            mRecipeNameTextView.setText(recipe.getName());
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {

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

        public void setRecipeData(List<Recipe> data) {
            mRecipes = data;
            notifyDataSetChanged();
        }
    }
}
