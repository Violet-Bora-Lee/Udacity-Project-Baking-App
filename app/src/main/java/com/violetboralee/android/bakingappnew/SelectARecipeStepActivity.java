package com.violetboralee.android.bakingappnew;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepActivity extends SingleFragmentActivity {

    public static final String EXTRA_RECIPE_ID = "com.violetboralee.android.bakingappnew.recipe_id";

    public static Intent newIntent(Context packageContext, int recipeId) {
        Intent intent = new Intent(packageContext, SelectARecipeStepActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 0);
        return SelectARecipeStepFragment.newInstance(recipeId);
    }
}
