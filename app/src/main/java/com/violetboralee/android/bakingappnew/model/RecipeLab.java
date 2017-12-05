package com.violetboralee.android.bakingappnew.model;

import android.content.Context;

import java.util.List;

/**
 * Created by bora on 15/11/2017.
 */

public class RecipeLab {
    private static RecipeLab sRecipeLab;
    private static List<Recipe> mRecipes;

    // Constructor
    private RecipeLab(Context context) {
    }

    public static RecipeLab get(Context context) {
        if (sRecipeLab == null) {
            sRecipeLab = new RecipeLab(context);
        }
        return sRecipeLab;
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
            if (step.getId() == stepId) {
                return step;
            }
        }
        return null;
    }

    public int getStepsCurrentIndex(int recipeId, Step step) {
        List<Step> steps = sRecipeLab.getSteps(recipeId);
        return steps.indexOf(step);
    }
}
