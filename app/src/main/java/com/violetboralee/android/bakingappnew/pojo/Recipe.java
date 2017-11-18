package com.violetboralee.android.bakingappnew.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bora on 16/11/2017.
 */

public class Recipe {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    private List<Step> steps;

    @SerializedName("servings")
    private int serving;

    @SerializedName("image")
    private String image;

    // Constructor
    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps,
                  int serving, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.serving = serving;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
