package com.raghavi.eggtimerproj;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView counter;
    Button control;
    boolean startScreen;
    int sec;
    int min;
    CountDownTimer countDownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialsie();

        onClickListeners();
    }

    @TargetApi(Build.VERSION_CODES.O)
    protected void initialsie() {
        seekBar = (SeekBar) findViewById(R.id.seek_Bar);
        seekBar.setEnabled(true);
        counter = (TextView) findViewById(R.id.counter_text_view);
        control = (Button) findViewById(R.id.button);
        seekBar.setMax(500);
        seekBar.setProgress(0);
        startScreen = true;
        sec=30;
        min=0;
    }

    protected void onClickListeners() {
        //seekbarListener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentPosition=seekBar.getProgress();
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {



                   sec=i%60;
                   min=i/60;


                counter.setText(Integer.toString(min)+":"+Integer.toString(sec));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Button Onclick
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (startScreen) {
                   // seekBar.animate().alpha(0);
                    seekBar.setEnabled(false);
                    control.setText("STOP");
                    startScreen = false;

                    //100 i.e. 1th of second added to keep the timer in sychro as the actual time it takes to go through the code is somewhat 8500
                    //milliseconds for 9000 milliseconds so we add the 100 millis
                    countDownTimer=new CountDownTimer((min*60+sec)*1000+100,1000)
                    {


                        @Override
                        public void onTick(long l) {
                            long total_sec=l/1000;
                            min= (int) (total_sec/60);
                            sec=(int) (total_sec%60);
                            counter.setText(min+":"+sec);

                        }

                        @Override
                        public void onFinish() {
                            seekBar.setEnabled(true);
                            seekBar.setProgress(30);
                            control.setText("GO!");
                            startScreen = true;
                            min=0;
                            sec=30;
                            countDownTimer.cancel();
                            counter.setText(min+":"+sec);
                            MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                            mediaPlayer.start();
                        }
                    }.start();

                } else {

                    startScreen = true;
                    seekBar.setEnabled(true);
                    seekBar.setProgress(30);
                    control.setText("GO!");


                    //min=0;
                    //sec=30;
                    countDownTimer.cancel();
                    counter.setText(min+":"+sec);

                }
            }
        });
    }


}