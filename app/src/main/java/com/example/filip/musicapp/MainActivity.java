package com.example.filip.musicapp;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //variáveis pro player
    private double mStartTime = 0;
    private double mFinalTime = 0;
    private android.os.Handler myHandler   = new Handler();
    private Button btnPause;
    private Button btnPlay;
    private Button btnStop;
    private TextView txtInicio;
    private TextView txtFim;
    private SeekBar seekbar;
    private MediaPlayer mediaPlayer;

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            mStartTime = mediaPlayer.getCurrentPosition();
            txtInicio.setText(String.format("%d min, %d sec",
                    java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) mStartTime - java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mStartTime)))));

            seekbar.setProgress((int) mStartTime);
            myHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtInicio = (TextView) findViewById(R.id.txtIni);
        txtFim = (TextView) findViewById(R.id.txtFim);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);
        seekbar = (SeekBar) findViewById(R.id.sbSeek);
        seekbar.setClickable(false);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                mFinalTime = mediaPlayer.getDuration();
                mStartTime = mediaPlayer.getCurrentPosition();
                txtFim.setText(String.format("%d min, %d sec",
                        java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mFinalTime),
                        java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds((long) mFinalTime) - java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes((long) mFinalTime))));
                double tempo = mFinalTime;
                seekbar.setMax(Integer.valueOf((int) tempo));
                seekbar.setProgress((int) mStartTime);
                myHandler.postDelayed(UpdateSongTime, 100);
                txtInicio.setText((String.valueOf(mediaPlayer.getCurrentPosition())));
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
            }
        });

    }
}
