package com.example.videostreamingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;
import java.net.URI;

public class VideosActivity extends AppCompatActivity {
    public VideoView mainvideo;
    public ProgressBar currentprogress;
    public ImageView playbtn;
    public Uri videouri;
    public boolean isPlaying;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        mainvideo = (VideoView)findViewById(R.id.videoView);
        videouri= Uri.parse(getIntent().getStringExtra("data"));
        mainvideo.setVideoURI(videouri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mainvideo);
        mainvideo.setMediaController(mediaController);
    }
}