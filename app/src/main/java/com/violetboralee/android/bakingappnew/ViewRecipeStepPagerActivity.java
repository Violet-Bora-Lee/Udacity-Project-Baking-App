package com.violetboralee.android.bakingappnew;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;

import java.util.List;

/**
 * Created by bora on 30/11/2017.
 */

public class ViewRecipeStepPagerActivity extends FragmentActivity {
    public static final String EXTRA_RECIPE_ID = "recipe_id";
    public static final String EXTRA_STEP_ID = "step_id";
    public static final String EXTRA_CURRENT_INDEX = "current_index";

    private ViewPager mViewPager;
    private List<Step> mSteps;

    public static Intent newIntent(Context packageContext, int recipeId, int stepId, int currentIndex) {
        Intent intent = new Intent(packageContext, ViewRecipeStepPagerActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        intent.putExtra(EXTRA_STEP_ID, stepId);
        intent.putExtra(EXTRA_CURRENT_INDEX, currentIndex);
        return intent;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_view_recipe_step_pager);

        final int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, 1);
        final int stepId = getIntent().getIntExtra(EXTRA_STEP_ID, 1);
        final int currentIndex = getIntent().getIntExtra(EXTRA_CURRENT_INDEX, 0);

        mSteps = RecipeLab.get(this).getSteps(recipeId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Step step = mSteps.get(position);
                return ViewRecipeStepFragment.newInstance(recipeId, step.getId(), step.getId());
            }

            @Override
            public int getCount() {
                return mSteps.size();
            }
        });

        for (int i = 0; i < mSteps.size(); i++) {
            if (mSteps.get(i).equals(currentIndex)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
