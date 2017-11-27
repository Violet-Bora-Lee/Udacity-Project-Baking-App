package com.violetboralee.android.bakingappnew.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.violetboralee.android.bakingappnew.retrofit.BakingAppClient;
import com.violetboralee.android.bakingappnew.retrofit.ServiceGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by bora on 15/11/2017.
 */

public class RecipeLab {
    private static RecipeLab sRecipeLab;
    private static List<Recipe> mRecipes;

    // Constructor
    private RecipeLab(Context context) {
//        mRecipes = fetchRecipeFromJson(context);
//        mRecipes = fetchRecipeFromTheInternet();

//        BakingAppClient service = ServiceGenerator.createService(BakingAppClient.class);
//
//        Call<List<Recipe>> call = service.getRecipes();
//
//        call.enqueue(new Callback<List<Recipe>>() {
//            @Override
//            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
//                if (response.isSuccessful()) {
//                    mRecipes = response.body();
//                } else {
//                    Log.e("Request failed: ", "Cannot request recipe json");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Recipe>> call, Throwable t) {
//                Log.e("Error fetching recipes", t.getMessage());
//
//            }
//        });

    }

    public static RecipeLab get(Context context) {
        if (sRecipeLab == null) {
            sRecipeLab = new RecipeLab(context);
        }
        return sRecipeLab;
    }

    private List<Recipe> fetchRecipeFromJson(Context context) {
        String recipeJson = null;

        try {
            InputStream is = context.getAssets().open("baking.json");
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

    private List<Recipe> fetchRecipeFromTheInternet() {
        BakingAppClient service = ServiceGenerator.createService(BakingAppClient.class);

        Call<List<Recipe>> call = service.getRecipes();

        try {
            List<Recipe> recipes = call.execute().body();
            return recipes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // getter of recipe list
    public List<Recipe> getRecipes() {
        return mRecipes;
    }

    public static void setRecipes(List<Recipe> recipes) {
        RecipeLab.mRecipes = recipes;
    }

    // getter of a recipe
    public Recipe getRecipe(int recipeId) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == recipeId) {
                return recipe;
            }
        }
        return null;
    }

    // getter of ingredient list
    public List<Ingredient> getIngredients(int recipeId) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == recipeId) {
                return recipe.getIngredients();
            }
        }
        return null;
    }

    // getter of a ingredient
    public Ingredient getIngredient(int recipeId, int stepId) {
        return sRecipeLab.getIngredients(recipeId).get(stepId);
    }

    // getter of step list
    public List<Step> getSteps(int recipeId) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == recipeId) {
                return recipe.getSteps();
            }
        }
        return null;
    }

    // getter of a step
    public Step getStep(int recipeId, int stepId) {
        List<Step> steps = sRecipeLab.getSteps(recipeId);
        for (Step step : steps) {
            if (step.getId() == stepId) {   // 13 ==
                return steps.get(stepId);
            }
        }
        return null;
    }
}
