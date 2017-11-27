package com.violetboralee.android.bakingappnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;

import java.util.List;

/**
 * Created by bora on 28/11/2017.
 */

public class ViewRecipeStepPagerActivity extends FragmentActivity {

    public static final String EXTRA_RECIPE_ID = "recipe_id";
    public static final String EXTRA_STEP_ID = "step_id";

    private ViewPager mViewPager;
    private List<Step> mSteps;


    public static Intent newIntent(Context packageContext, int recipeId, int stepId) {
        Intent intent = new Intent(packageContext, ViewRecipeStepPagerActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_view_recipe_step_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_view_recipe_step_pager_view_pager);

        RecipeLab recipeLab = RecipeLab.get(this);
        final int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 1);
        final int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, 1);

        mSteps = recipeLab.getSteps(recipeId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return ViewRecipeStepFragment.newInstance(recipeId, stepId);
            }

            @Override
            public int getCount() {
                return mSteps.size();
            }
        });
    }
}
