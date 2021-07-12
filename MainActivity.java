package com.example.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView playerPosition,playerDuration;
    SeekBar seekBar;
    ImageView btnPlay, btnPause, btnRewind, btnForward;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerPosition = findViewById(R.id.player_begin_position);
        playerDuration = findViewById(R.id.player_duration);
        seekBar = findViewById(R.id.seek_bar);
        btnPlay = findViewById(R.id.btn_play);
        btnPause = findViewById(R.id.btn_pause);
        btnRewind = findViewById(R.id.btn_rewind);
        btnForward = findViewById(R.id.btn_forward);

        mediaPlayer = MediaPlayer.create(this ,R.raw.davido_fem_official_video_aac_58449);

        runnable = new Runnable() {
            @Override
            public void run() {

                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                 handler.postDelayed(this, 500);

            }
        };
         int duration = mediaPlayer.getDuration();

         String sDuration = convertFormat(duration);

         playerDuration.setText(sDuration);
          btnPlay.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  btnPlay.setVisibility(View.GONE);

                  btnPause.setVisibility(View.VISIBLE);

                  mediaPlayer.start();

                  seekBar.setMax(mediaPlayer.getDuration());

                  handler.postDelayed(runnable, 0);

              }
          });

          btnPause.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  btnPause.setVisibility(View.GONE);
                   btnPlay.setVisibility(View.VISIBLE);
                    mediaPlayer.pause();

                    handler.removeCallbacks(runnable);
              }
          });
          btnForward.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                int currentPos = mediaPlayer.getCurrentPosition();

                int duration = mediaPlayer.getDuration();

                if(mediaPlayer.isPlaying() && duration != currentPos){

                    currentPos = currentPos + 5000;

                    playerPosition.setText(convertFormat(currentPos));
                    mediaPlayer.seekTo(currentPos);
                }
                      }
          });
          btnRewind.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  int currentPos = mediaPlayer.getCurrentPosition();


                  if(mediaPlayer.isPlaying() && currentPos > 5000){
                      currentPos = currentPos - 5000;

                      playerPosition.setText(convertFormat(currentPos));

                      mediaPlayer.seekTo(currentPos);
                  }
              }
          });
          seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
              @Override
              public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
              }

              @Override
              public void onStartTrackingTouch(SeekBar seekBar) {

              }

              @Override
              public void onStopTrackingTouch(SeekBar seekBar) {

              }
          });
          mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
              @Override
              public void onCompletion(MediaPlayer mediaPlayer) {
                  btnPause.setVisibility(View.GONE);
                  btnPlay.setVisibility(View.VISIBLE);
                  mediaPlayer.seekTo(0);
              }
          });
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration)
                ,TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}