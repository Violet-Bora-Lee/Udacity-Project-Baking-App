package com.violetboralee.android.bakingappnew;

import android.support.v4.app.Fragment;

import com.violetboralee.android.bakingappnew.pojo.Step;

public class ViewRecipeStepActivity extends SingleFragmentActivity {

    public static final String EXTRA_STEP = "com.violetboralee.android.bakingappnew.step";

//    public static Intent newIntent(Context packageContext, int stepId) {
//        Intent intent = new Intent(packageContext, ViewRecipeStepActivity.class);
//        intent.putExtra(EXTRA_STEP, stepId);
//        return intent;
//    }

    @Override
    protected Fragment createFragment() {
        Step step = (Step) getIntent().getSerializableExtra(EXTRA_STEP);
        return ViewRecipeStepFragment.newInstance(step);
    }


}
