package com.violetboralee.android.bakingappnew;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.violetboralee.android.bakingappnew.pojo.Ingredient;
import com.violetboralee.android.bakingappnew.pojo.Recipe;
import com.violetboralee.android.bakingappnew.pojo.Step;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bora on 15/11/2017.
 */

public class RecipeLab {
    private static RecipeLab sRecipeLab;

    private static List<Recipe> mRecipes;

    // Constructor
    private RecipeLab(Context context) {

        mRecipes = fetchRecipeFromJson(context);
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

    // getter of recipe list
    public List<Recipe> getRecipes() {
        return mRecipes;
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
    public Ingredient getIngredient(int id) {
        List<Ingredient> ingredients = getIngredients(id);
        for (Ingredient ingredient : ingredients) {
            return ingredients.get(id);
        }
        return null;
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
    public Step getStep(int stepId) {
        List<Step> steps = getSteps(stepId);
        for (Step step : steps) {
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }
}
