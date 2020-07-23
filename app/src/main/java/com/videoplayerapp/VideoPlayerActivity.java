package com.videoplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    VideoView player;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        player = findViewById(R.id.player);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        position = getIntent().getIntExtra("pos",-1);
        getSupportActionBar().hide();
        
        playerVideo();

    }

    private void playerVideo() {

        MediaController controller = new MediaController(this);
        controller.setAnchorView(player);

        player.setMediaController(controller);
        player.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position)));
        player.requestFocus();

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
player.start();
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                player.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position = position+1)));
                player.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stopPlayback();
    }
}
