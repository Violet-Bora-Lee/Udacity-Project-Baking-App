package com.violetboralee.android.bakingappnew.retrofit;

import com.violetboralee.android.bakingappnew.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bora on 27/11/2017.
 */

public interface BakingAppClient {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
