package com.violetboralee.android.bakingappnew;

import android.support.v4.app.Fragment;

/**
 * Created by bora on 17/11/2017.
 */

public class SelectARecipeStepActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SelectARecipeStepFragment();
    }
}
