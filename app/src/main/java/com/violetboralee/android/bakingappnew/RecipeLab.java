package com.violetboralee.android.bakingappnew;

import android.content.Context;

import com.violetboralee.android.bakingappnew.pojo.Ingredient;
import com.violetboralee.android.bakingappnew.pojo.Recipe;
import com.violetboralee.android.bakingappnew.pojo.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bora on 15/11/2017.
 */

public class RecipeLab {
    private static RecipeLab sRecipeLab;

    private static List<Step> mSteps;
    private static List<Ingredient> mIngredients;
    private static List<Recipe> mRecipes;

    // Constructor
    private RecipeLab(Context context) {
        mRecipes = new ArrayList<>();
        mIngredients = new ArrayList<>();
        mSteps = new ArrayList<>();

        // test data
        Ingredient ingredient1of1 = new Ingredient(2, "CUP", "Graham Cracker curmbs");
        Ingredient ingredient2of1 = new Ingredient(6, "TBLSP", "unsalted butter, melted");
        Ingredient ingredient3of1 = new Ingredient(0.5, "CUP", "granulated sugar");
        mIngredients.add(ingredient1of1);
        mIngredients.add(ingredient2of1);
        mIngredients.add(ingredient3of1);

        Step step1of1 = new Step(0, "Recipe Introduction", "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", null);
        Step step2of1 = new Step(1, "Starting prep", "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.", null, null);
        Step step3of1 = new Step(2, "Prep the cookie crust.", "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", null);
        mSteps.add(step1of1);
        mSteps.add(step2of1);
        mSteps.add(step3of1);

        Recipe recipe = new Recipe(1, "Nutella Pie", mIngredients, mSteps, 8, null);
        mRecipes.add(recipe);

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

    // getter of a recipe
    public Recipe getRecipe(int id) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == id) {
                return recipe;
            }
        }
        return null;
    }

    // getter of ingredient list
    public List<Ingredient> getIngredients(int id) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == id) {
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
    public List<Step> getSteps(int id) {
        for (Recipe recipe : mRecipes) {
            if (recipe.getId() == id) {
                return recipe.getSteps();
            }
        }
        return null;
    }

    // getter of a step
    public Step getStep(int id) {
        List<Step> steps = getSteps(id);
        for (Step step : steps) {
            if (step.getId() == id) {
                return step;
            }
        }
        return null;
    }
}
