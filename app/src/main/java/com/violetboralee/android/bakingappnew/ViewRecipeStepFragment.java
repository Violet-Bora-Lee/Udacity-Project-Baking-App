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
import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;

/**
 * Created by bora on 13/11/2017.
 */

public class ViewRecipeStepFragment extends Fragment {

    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private TextView mShortDescription;

    private TextView mDescription;

    private int recipeId;
    private int stepId;


    public static ViewRecipeStepFragment newInstance(int recipeId, int stepId) {
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_RECIPE_ID, recipeId);
        bundle.putInt(ARG_STEP_ID, stepId);

        ViewRecipeStepFragment fragment = new ViewRecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_recipe_step, container, false);

        recipeId = getArguments().getInt(ARG_RECIPE_ID);
        stepId = getArguments().getInt(ARG_STEP_ID);

        //        mPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.exo_player_view);
        mShortDescription = (TextView) v.findViewById(R.id.tv_short_description);

        mDescription = (TextView) v.findViewById(R.id.tv_description);

        updateUI(recipeId, stepId);

        return v;
    }

    private void updateUI(int recipeId, int stepId) {
        RecipeLab recipeLab = RecipeLab.get(getContext());
//        List<Step> steps = recipeLab.getSteps(recipeId);
        Step step = recipeLab.getStep(recipeId, stepId);

        mShortDescription.setText(step.getShortDescription());
        mDescription.setText(step.getDescription());

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
//        releasePlayer();
    }
}
