package com.violetboralee.android.bakingappnew.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.violetboralee.android.bakingappnew.IdlingResource.SimpleIdlingResource;

/**
 * Created by bora on 19/11/2017.
 */

public class SelectARecipeActivity extends SingleFragmentActivity {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected Fragment createFragment() {
        return new SelectARecipeFragment();
    }
}
