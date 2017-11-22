package com.violetboralee.android.bakingappnew.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bora on 16/11/2017.
 */

public class Ingredient {
    @SerializedName("quantity")
    private double quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    // Constructor
    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }



    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

}
