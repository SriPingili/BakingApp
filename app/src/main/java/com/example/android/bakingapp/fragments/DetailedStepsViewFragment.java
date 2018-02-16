package com.example.android.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.utilities.BakingAppUtil;
import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that displays the detailed steps to the user. This
 * includes the video and description of the step
 * <p>
 * Used the Udacity videos as reference for the exoplayer
 */
public class DetailedStepsViewFragment extends android.support.v4.app.Fragment {
    private Steps steps;
    protected static SimpleExoPlayerView mPlayerView;
    private static SimpleExoPlayer mExoPlayer;
    private static long position;
    private static boolean mPlayVideoWhenForegrounded = false;
    private static final String POSITION_KEY = "position_key";
    private static final String PLAY_VIDEO_KEY = "play_video_key";

    @BindView(R.id.step_detailed_description_text_id)
    protected TextView detailedDescriptionTextView;
    @BindView(R.id.thumnail_id)
    ImageView thumbnailImageView;

    /**
     * reference for exoplayer state
     * https://stackoverflow.com/questions/45481775/exoplayer-restore-state-when-resumed/45482017#45482017
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_detailed_step_view, container, false);
        ButterKnife.bind(this, rootView);
        position = C.TIME_UNSET;
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(POSITION_KEY, C.TIME_UNSET);
            mPlayVideoWhenForegrounded = savedInstanceState.getBoolean(PLAY_VIDEO_KEY, false);
        }
        mPlayerView = rootView.findViewById(R.id.playerView);
        steps = (Steps) getArguments().getSerializable(BakingAppUtil.STEPS_DATA_KEY);
        if (!steps.getVideoURL().isEmpty()) {
            initializePlayer();
        } else {
            mPlayerView.setVisibility(View.GONE);
        }
        detailedDescriptionTextView.setText(steps.getDescription().toString());
        final String thumbnailURl = steps.getThumbnailURL();
        if ((!thumbnailURl.isEmpty()) && (thumbnailURl.contains(".jpeg") || thumbnailURl.contains(".png"))) {
            thumbnailImageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(Uri.parse(thumbnailURl)).into(thumbnailImageView);
        } else
            thumbnailImageView.setVisibility(View.GONE);
        return rootView;
    }

    public void initializePlayer() {
        mPlayerView.setVisibility(View.VISIBLE);
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(steps.getVideoURL()), new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (position != C.TIME_UNSET)
                mExoPlayer.seekTo(position);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(mPlayVideoWhenForegrounded);
        }
    }

    public static void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        position = mExoPlayer.getCurrentPosition();
        mPlayVideoWhenForegrounded = mExoPlayer.getPlayWhenReady();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION_KEY, position);
        outState.putBoolean(PLAY_VIDEO_KEY, mPlayVideoWhenForegrounded);
    }
}