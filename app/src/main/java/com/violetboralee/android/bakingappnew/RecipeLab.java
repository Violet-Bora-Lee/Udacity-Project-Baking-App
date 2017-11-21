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
    private static List<Ingredient> mIngredients1;
    private static List<Recipe> mRecipes;

    // Constructor
    private RecipeLab(Context context) {

//        // Constructor
//    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps,
//        int serving, String image) {
//            this.id = id;
//            this.name = name;
//            this.ingredients = ingredients;
//            this.steps = steps;
//            this.serving = serving;
//            this.image = image;
//        }

        mRecipes = new ArrayList<Recipe>();
        mIngredients1 = new ArrayList<Ingredient>();
        mSteps = new ArrayList<Step>();

        // test data
        Recipe recipe1 = new Recipe(1, "Nutella Pie", mIngredients1, mSteps, 8, null);
        mRecipes.add(recipe1);


        Ingredient ingredient1of1 = new Ingredient(2, "CUP", "Graham Cracker curmbs");
        Ingredient ingredient2of1 = new Ingredient(6, "TBLSP", "unsalted butter, melted");
        Ingredient ingredient3of1 = new Ingredient(0.5, "CUP", "granulated sugar");
        mIngredients1.add(ingredient1of1);
        mIngredients1.add(ingredient2of1);
        mIngredients1.add(ingredient3of1);

        Step step0of1 = new Step(0, "Recipe Introduction", "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", null);
        Step step1of1 = new Step(1, "Starting prep", "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.", null, null);
        Step step2of1 = new Step(2, "Prep the cookie crust.", "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", null);
        mSteps.add(step0of1);
        mSteps.add(step1of1);
        mSteps.add(step2of1);

//        Recipe recipe2 = new Recipe(2, "Brownies", mIngredients1, mSteps, 8, null);
//        mRecipes.add(recipe2);
//
//        Ingredient ingredient1of2 = new Ingredient(350, "G", "Bittersweet chocolate (60-70% cacao)");
//        Ingredient ingredient2of2 = new Ingredient(226, "G", "unsalted butter");
//        Ingredient ingredient3of2 = new Ingredient(300, "G", "granulated sugar");
//        Ingredient ingredient4of2 = new Ingredient(100, "G", "light brown sugar");
//        Ingredient ingredient5of2 = new Ingredient(5, "UNIT", "large eggs");
//        mIngredients1.add(ingredient1of2);
//        mIngredients1.add(ingredient2of2);
//        mIngredients1.add(ingredient3of2);
//        mIngredients1.add(ingredient4of2);
//        mIngredients1.add(ingredient5of2);
//
//        Step step0of2 = new Step(0, "Recipe Introduction", "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc33_-intro-brownies/-intro-brownies.mp4", null);
//        Step step1of2 = new Step(1, "Starting prep", "1. Preheat the oven to 350�F. Butter the bottom and sides of a 9\"x13\" pan.", null, null);
//        Step step2of2 = new Step(2, "Melt butter and bittersweet chocolate.", "2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4", null);
//        mSteps.add(step0of2);
//        mSteps.add(step1of2);
//        mSteps.add(step2of2);
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

//    // getter of a ingredient
//    public Ingredient getIngredient(int id) {
//        List<Ingredient> ingredients = getIngredients(id);
//        for (Ingredient ingredient : ingredients) {
//            return ingredients.get(id);
//        }
//        return null;
//    }

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
