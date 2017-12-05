package com.violetboralee.android.bakingappnew;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.violetboralee.android.bakingappnew.model.Recipe;
import com.violetboralee.android.bakingappnew.model.RecipeLab;
import com.violetboralee.android.bakingappnew.model.Step;

import java.util.List;

/**
 * Created by bora on 13/11/2017.
 */

public class ViewRecipeStepFragment extends Fragment
        implements ExoPlayer.EventListener, View.OnClickListener {
    public static final String ARG_CURRENT_INDEX = "current_index";
    private static final String LOG_TAG = ViewRecipeStepFragment.class.getSimpleName();
    private static final String ARG_RECIPE_ID = "recipe_id";
    private static final String ARG_STEP_ID = "step_id";
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private ProgressBar mBufferingProgressBar;

    private ImageView mThumbnailImg;

    private TextView mFoodName;
    private TextView mShortDescription;
    private TextView mDescription;

    private List<Step> mSteps;
    private Step mStep;
    private int mRecipeId;
    private int mStepId;
    private int mSizeOfSteps;
    private int mCurrentIndex;


    public static ViewRecipeStepFragment newInstance(int recipeId, int stepId, int currentIndex) {
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_RECIPE_ID, recipeId);
        bundle.putInt(ARG_STEP_ID, stepId);
        bundle.putInt(ARG_CURRENT_INDEX, currentIndex);

        ViewRecipeStepFragment fragment = new ViewRecipeStepFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_recipe_step, container, false);

        mRecipeId = getArguments().getInt(ARG_RECIPE_ID);
        mStepId = getArguments().getInt(ARG_STEP_ID);

        mSteps = RecipeLab.get(getActivity()).getSteps(mRecipeId);
        mSizeOfSteps = mSteps.size();
        mStep = RecipeLab.get(getActivity()).getStep(mRecipeId, mStepId);
        mCurrentIndex = getArguments().getInt(ARG_CURRENT_INDEX);

        // Initialize the player view.
        mPlayerView = (SimpleExoPlayerView) v.findViewById(R.id.exo_player_view);
        mBufferingProgressBar = (ProgressBar) v.findViewById(R.id.pb_buffering_exo_player);

        // Initialize the media session.
        initializeMediaSession();

        // Initialize the player.
        String videoURL = getVideoUri(mStep);
        if (videoURL != "") {
            initializePlayer(Uri.parse(videoURL));
        } else if (videoURL == "") {
            initializePlayer(null);
        }


        // Initialize the views.
        mFoodName = (TextView) v.findViewById(R.id.tv_food_name);
        mThumbnailImg = (ImageView) v.findViewById(R.id.iv_thumbnail_image);
        mShortDescription = (TextView) v.findViewById(R.id.tv_short_description);
        mDescription = (TextView) v.findViewById(R.id.tv_description);

        updateUI(mRecipeId, mStepId);

        Button prevStep = (Button) v.findViewById(R.id.btn_previous);
        prevStep.setOnClickListener(this);

        Button nextStep = (Button) v.findViewById(R.id.btn_next);
        nextStep.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(mRecipeId, mStepId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_previous:
//                Toast.makeText(getContext(), "Prev Button", Toast.LENGTH_LONG).show();
                if (mStepId > 0) {
                    if (mExoPlayer != null) {
                        mExoPlayer.stop();
                    }
                    mCurrentIndex--; // Decrease the index as long as the index is greater than 0
                    mStepId--;
                } else {
                    Toast.makeText(getContext(), "You are in a first step!", Toast.LENGTH_LONG).show();
                }
                // TODO: Change the fragment
                return;

            case R.id.btn_next:
//                Toast.makeText(getContext(), "Next Button", Toast.LENGTH_LONG).show();
                if (mCurrentIndex < mSizeOfSteps - 1) {
                    mCurrentIndex++; // Increase the index as long as the index remains <= the size of the List of Steps
                } else {
                    Toast.makeText(getContext(), "Your are in the last step!", Toast.LENGTH_LONG).show();
                }
                // TODO: Change the fragment
                return;
        }

    }

    private void updateUI(int recipeId, int stepId) {
        RecipeLab recipeLab = RecipeLab.get(getContext());
        Recipe recipe = recipeLab.getRecipe(recipeId);
        Step step = recipeLab.getStep(recipeId, stepId);

        String imgUrl = step.getThumbnailURL();
        String shortDescription = step.getShortDescription();
        String description = step.getDescription();

        if (imgUrl != "") {
            Uri imgUri = Uri.parse(imgUrl).buildUpon().build();
            Picasso.with(getContext()).load(imgUri).into(mThumbnailImg);
        }

        mFoodName.setText(recipe.getName());
        mShortDescription.setText(shortDescription);
        mDescription.setText(description);

    }



    /**
     * Initialize the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getActivity(), LOG_TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
    }

    private String getVideoUri(Step step) {
        if (step.getVideoURL() != null) {
            return step.getVideoURL();
        } else if (step.getThumbnailURL() != null) {
            return step.getThumbnailURL();
        } else if (step.getVideoURL() == "" && step.getThumbnailURL() == "") {
            return null;
        }
        return null;
    }



    /**
     * Initialize ExoPlayer
     *
     * @param mediaUri The URI of the sample to play
     */
    private void initializePlayer(Uri mediaUri) {
        if (mediaUri != null) {
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);
                mExoPlayer.addListener(this);
                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        } else if (mediaUri == null) {
            mBufferingProgressBar.setVisibility(View.INVISIBLE);
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(
                    getResources(), R.drawable.no_video_available)
            );
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

    // ExoPlayer Event Listener
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if (playbackState == ExoPlayer.STATE_BUFFERING) {
            mBufferingProgressBar.setVisibility(View.VISIBLE);
        } else {
            if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                Log.d(LOG_TAG, "onPlayerStateChanged: PLAYING");
                mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                        mExoPlayer.getCurrentPosition(), 1f);
                mBufferingProgressBar.setVisibility(View.INVISIBLE);
            } else if ((playbackState == ExoPlayer.STATE_READY)) {
                Log.d(LOG_TAG, "onPlayerStateChanged: PAUSED");
                mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                        mExoPlayer.getCurrentPosition(), 1f);
                mBufferingProgressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    /**
     * Media Session Callbacks, where all external clients control the player
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
