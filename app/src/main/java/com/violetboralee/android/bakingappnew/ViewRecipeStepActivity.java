package com.violetboralee.android.bakingappnew;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ViewRecipeStepActivity extends SingleFragmentActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";
    public static final String EXTRA_STEP_ID = "step_id";

    public static Intent newIntent(Context packageContext, int recipeId, int stepId) {
        Intent intent = new Intent(packageContext, ViewRecipeStepActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 1);
        int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, 1);
        return ViewRecipeStepFragment.newInstance(recipeId, stepId);
    }


}
