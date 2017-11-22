package com.violetboralee.android.bakingappnew;

import android.support.v4.app.Fragment;

/**
 * Created by bora on 19/11/2017.
 */

public class SelectARecipeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new SelectARecipeFragment();
    }
}
