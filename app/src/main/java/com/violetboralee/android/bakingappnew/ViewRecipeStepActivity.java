package com.violetboralee.android.bakingappnew;

import android.support.v4.app.Fragment;

public class ViewRecipeStepActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ViewRecipeStepFragment();
    }


}
