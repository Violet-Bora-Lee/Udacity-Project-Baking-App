package com.violetboralee.android.bakingappnew;

import android.support.v4.app.Fragment;

import com.violetboralee.android.bakingappnew.pojo.Recipe;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepActivity extends SingleFragmentActivity {

    public static final String EXTRA_RECIPE = "com.violetboralee.android.bakingappnew.recipe";

//    public static Intent newIntent(Context packageContext, Recipe recipe) {
//        Intent intent = new Intent(packageContext, SelectARecipeStepActivity.class);
//        intent.putExtra(EXTRA_RECIPE, recipe);
//        return intent;
//    }

    @Override
    protected Fragment createFragment() {
        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
        return SelectARecipeStepFragment.newInstance(recipe);
    }
}
