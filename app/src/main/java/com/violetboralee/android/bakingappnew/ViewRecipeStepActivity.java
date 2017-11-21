package com.violetboralee.android.bakingappnew;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ViewRecipeStepActivity extends SingleFragmentActivity {

    public static final String EXTRA_STEP_ID = "com.violetboralee.android.bakingappnew.step_id";

    public static Intent newIntent(Context packageContext, int stepId) {
        Intent intent = new Intent(packageContext, ViewRecipeStepActivity.class);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new ViewRecipeStepFragment();
    }


}
