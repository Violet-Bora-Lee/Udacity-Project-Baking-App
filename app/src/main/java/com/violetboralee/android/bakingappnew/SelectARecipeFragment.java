package com.violetboralee.android.bakingappnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.violetboralee.android.bakingappnew.model.Recipe;
import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.retrofit.BakingAppClient;
import com.violetboralee.android.bakingappnew.retrofit.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bora on 19/11/2017.
 */

public class SelectARecipeFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;
    private TextView mErrorMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // TODO: 랜드스케이프모드시 그리드레이아웃으로 바뀌게 레이아웃파일 수정 및 코드 연동
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_a_recipe, container, false);

        mRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipes_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mErrorMessage = (TextView) view.findViewById(R.id.tv_error_message_display);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        final RecipeLab recipeLab = RecipeLab.get(getActivity());

        BakingAppClient service = ServiceGenerator.createService(BakingAppClient.class);

        Call<List<Recipe>> call = service.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {

                    RecipeLab.setRecipes(response.body());
                    List<Recipe> recipes = recipeLab.getRecipes();
                    if (mAdapter == null) {
                        mAdapter = new RecipeAdapter(recipes);
                        mRecipeRecyclerView.setAdapter(mAdapter);
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("Request failed: ", "Cannot request recipe json");
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("Error fetching recipes", t.getMessage());

                showErrorMessage();
            }
        });

    }

    private void showErrorMessage() {
        mRecipeRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);

    }


    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mRecipeNameTextView, mRecipeServingTextView, mRecipeStepTextView;
        private Recipe mRecipe;

        public RecipeHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mImageView = (ImageView) itemView.findViewById(R.id.list_item_recipe_image);
            mRecipeNameTextView = (TextView) itemView.findViewById(R.id.list_item_recipe_name_text_view);
            mRecipeServingTextView = (TextView) itemView.findViewById(R.id.list_item_recipe_serving_text_view);
            mRecipeStepTextView = (TextView) itemView.findViewById(R.id.list_item_recipe_step_number_text_view);

        }

        public void bindRecipe(Recipe recipe) {
            mRecipe = recipe;

            int serving = recipe.getServing();
            String servingString = String.valueOf(serving) + " servings";

            int stepNumber = recipe.getSteps().size();
            String stepNumberString = String.valueOf(stepNumber) + " steps";

            mRecipeNameTextView.setText(recipe.getName());
            mRecipeServingTextView.setText(servingString);
            mRecipeStepTextView.setText(stepNumberString);
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
