package com.zeeice.bakingapp.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.ParsableNalUnitBitArray;
import com.google.android.exoplayer2.util.Util;
import com.zeeice.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Oriaje on 25/06/2017.
 */

public class PlayVideoFragment extends Fragment implements ExoPlayer.EventListener{

    @BindView(R.id.video_view)
    SimpleExoPlayerView playerView;

    SimpleExoPlayer player;

    @BindView(R.id.video_steps)
    TextView stepView;

    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_STEPS = "video_steps";

    String url;
    String steps;
    Boolean playWhenReady;
    private long playbackPosition;
    int currentWindow;

    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey(VIDEO_URL) && getArguments().containsKey(VIDEO_STEPS))
        {
            url = getArguments().getString(VIDEO_URL);
            steps = getArguments().getString(VIDEO_STEPS);
        }
    }

//    private void hideSystemUI() {
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//    }
//    private void initializeMediaSession() {
//        mediaSession = new MediaSessionCompat(getContext(), "sdsfsfds");
//        mediaSession.setFlags(
//                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
//                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
//        mediaSession.setMediaButtonReceiver(null);
//        stateBuilder = new PlaybackStateCompat.Builder()
//                .setActions(
//                        PlaybackStateCompat.ACTION_PLAY |
//                                PlaybackStateCompat.ACTION_PAUSE |
//                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
//                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
//        mediaSession.setPlaybackState(stateBuilder.build());
//        mediaSession.setCallback(new MediaSessionCallback());
//        mediaSession.setActive(true);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_play_video,container,false);

       // playerView = (SimpleExoPlayerView)rootView.findViewById(R.id.video_view);
        //stepView = (TextView)rootView.findViewById(R.id.video_steps);
        //progressBar = (ProgressBar)rootView.findViewById(R.id.loading_indicator);

        unbinder = ButterKnife.bind(this,rootView);

        stepView.setText(steps);

  //      initializeMediaSession();
        initializePlayer();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    private void initializePlayer() {

        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

//            player = ExoPlayerFactory.newSimpleInstance(
//                    getActivity(),new DefaultTrackSelector(),new DefaultLoadControl()
//            );

            playerView.setPlayer(player);

            player.addListener(this);
            player.setPlayWhenReady(true);
            player.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(url);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, true);
        }
    }
    private MediaSource buildMediaSource(Uri uri)
    {
        String userAgent = Util.getUserAgent(getActivity(),"PlayVideoFragment");

        return new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(getActivity(),userAgent),
                new DefaultExtractorsFactory(),null,null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    //    progressBar.setVisibility(isLoading? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if(playbackState == ExoPlayer.STATE_BUFFERING)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
        releasePlayer();
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

}
