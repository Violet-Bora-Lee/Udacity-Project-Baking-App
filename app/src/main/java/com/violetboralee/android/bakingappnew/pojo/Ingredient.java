package com.violetboralee.android.bakingappnew.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bora on 16/11/2017.
 */

public class Ingredient
//        implements Parcelable
{
    //    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
//        @Override
//        public Ingredient createFromParcel(Parcel in) {
//            return new Ingredient(in);
//        }
//
//        @Override
//        public Ingredient[] newArray(int size) {
//            return new Ingredient[size];
//        }
//    };
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


//    protected Ingredient(Parcel in) {
//        quantity = in.readDouble();
//        measure = in.readString();
//        ingredient = in.readString();
//    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeDouble(quantity);
//        dest.writeString(measure);
//        dest.writeString(ingredient);
//    }
}
