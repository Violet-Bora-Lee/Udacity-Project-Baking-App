package com.violetboralee.android.bakingappnew;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.violetboralee.android.bakingappnew.pojo.Step;

import java.util.List;

/**
 * Created by bora on 13/11/2017.
 */

public class ViewRecipeStepFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private TextView mShortDescription;

    private TextView mDescription;

    private List<Step> mSteps;
    private Step mStep;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int recipeId;
//        int stepId = getActivity().getIntent().getIntExtra(ViewRecipeStepActivity.EXTRA_STEP_ID, 0);
//        mSteps = RecipeLab.get(getActivity()).getSteps(recipeId);
//        mStep = RecipeLab.get(getActivity()).getStep(stepId);

    }


//    mRecipes = new ArrayList<Recipe>();
//    mIngredients1 = new ArrayList<Ingredient>();
//    mSteps = new ArrayList<Step>();
//
//    // test data
//    Recipe recipe1 = new Recipe(1, "Nutella Pie", mIngredients1, mSteps, 8, null);
//        mRecipes.add(recipe1);
//
//
//    Ingredient ingredient1of1 = new Ingredient(2, "CUP", "Graham Cracker curmbs");
//    Ingredient ingredient2of1 = new Ingredient(6, "TBLSP", "unsalted butter, melted");
//    Ingredient ingredient3of1 = new Ingredient(0.5, "CUP", "granulated sugar");
//        mIngredients1.add(ingredient1of1);
//        mIngredients1.add(ingredient2of1);
//        mIngredients1.add(ingredient3of1);
//
//    Step step0of1 = new Step(0, "Recipe Introduction", "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", null);
//    Step step1of1 = new Step(1, "Starting prep", "1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.", null, null);
//    Step step2of1 = new Step(2, "Prep the cookie crust.", "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4", null);
//        mSteps.add(step0of1);
//        mSteps.add(step1of1);
//        mSteps.add(step2of1);
//


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_recipe_step, container, false);

//        mPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.exo_player_view);
        mShortDescription = (TextView) v.findViewById(R.id.tv_short_description);
        mShortDescription.setText(mStep.getShortDescription());

        mDescription = (TextView) v.findViewById(R.id.tv_description);
        mDescription.setText(mStep.getDescription());


        return v;
    }

    /**
     * Initialize ExoPlayer
     *
     * @param mediaUri The URI of the sample to play
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
