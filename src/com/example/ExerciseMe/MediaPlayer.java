package com.example.ExerciseMe;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaPlayer extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer);

        VideoView videoHolder = new VideoView(this);
        videoHolder.setMediaController(new MediaController(this));
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.pushup);
        videoHolder.setVideoURI(video);
        setContentView(videoHolder);
        videoHolder.start();
    }
}