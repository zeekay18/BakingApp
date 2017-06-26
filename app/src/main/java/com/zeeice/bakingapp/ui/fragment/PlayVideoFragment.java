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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
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


/**
 * Created by Oriaje on 25/06/2017.
 */

public class PlayVideoFragment extends Fragment {

    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;

    TextView stepView;

    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_STEPS = "video_steps";

    String url;
    String steps;
    Boolean playWhenReady;
    private long playbackPosition;
    int currentWindow;

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

        Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();

        playerView = (SimpleExoPlayerView)rootView.findViewById(R.id.video_view);
        stepView = (TextView)rootView.findViewById(R.id.video_steps);

        stepView.setText(steps);

  //      initializeMediaSession();
        initializePlayer();
        return rootView;
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
    class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            //player.setPlayWhenReady(true);
        }

        @Override
        public void onSkipToPrevious() {
            //restExoPlayer(0, false);
        }
    }

}