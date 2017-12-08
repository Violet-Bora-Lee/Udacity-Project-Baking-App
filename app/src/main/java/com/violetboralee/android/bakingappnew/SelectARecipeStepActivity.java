package com.violetboralee.android.bakingappnew;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepActivity extends SingleFragmentActivity
        implements SelectARecipeStepFragment.Callbacks {

    public static final String EXTRA_RECIPE_ID = "recipe_id";

    public static Intent newIntent(Context packageContext, int recipeId) {
        Intent intent = new Intent(packageContext, SelectARecipeStepActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int RecipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 1);
        return SelectARecipeStepFragment.newInstance(RecipeId);
    }

    @Override
    protected int getLayoutResId() {
        // For devices
        // that are under sw600dp(phone interface), it will return @layout/activity_fragment(single-pane layout)
        // that are over a sw600dp(table interface), it will return @layout/activity_twopane(two-pane layout)
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onStepSelected(int recipeId, Step step) {
        // For the devices that are under sw600dp
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ViewRecipeStepActivity
                    .newIntent(
                            this,
                            recipeId,
                            step.getId(),
                            RecipeLab.get(this).getStepsCurrentIndex(recipeId, step)
                    );
            startActivity(intent);

        } else { // For the devices that are over a sw600dp
            Fragment newDetail = ViewRecipeStepFragment.newInstance(
                    recipeId,
                    step.getId(),
                    RecipeLab.get(this).getStepsCurrentIndex(recipeId, step));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}
