package com.example.mychessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class PlayerActivity extends AppCompatActivity {
    //Initializing total time for each player.
    private static long START_TIME_IN_MILLIS_1;
    private static long START_TIME_IN_MILLIS_2;

    //Initializing the buttons
    private Button b1;
    private Button b2;
    private Button reset;
    private Button pause;
    private Button home;

    //Initializing the timers
    private CountDownTimer mCountDownTimer1;
    private CountDownTimer mCountDownTimer2;

    //Boolean Running variables for each timer
    private Boolean mR1=false;
    private Boolean mR2=false;

    //Key to determine status of clock
    private int key=0;

    //Time left for each player
    private long mTimeLeftInMillis1;
    private long mTimeLeftInMillis2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        START_TIME_IN_MILLIS_1=300000;
        START_TIME_IN_MILLIS_2=300000;

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        reset= findViewById(R.id.reset);
        pause= findViewById(R.id.pause);
        home = findViewById(R.id.home);

        mTimeLeftInMillis1 = START_TIME_IN_MILLIS_1;
        mTimeLeftInMillis2 = START_TIME_IN_MILLIS_2;

        //Setting color for each button
        b1.setBackgroundColor(Color.BLACK);
        b2.setBackgroundColor(Color.BLACK);

        //When button1 is clicked
        b1.setOnClickListener(v -> {
            if(mR1) {
                pause1();
                start2();
            }
            else if(!mR2){
                start2();
            }
            b2.setBackgroundColor(Color.WHITE);
            b2.setTextColor(Color.BLACK);
            b1.setBackgroundColor(Color.BLACK);
            b1.setTextColor(Color.WHITE);
        });

        //When button2 is clicked
        b2.setOnClickListener(v -> {
            if(mR2){
                pause2();
                start1();
            }
            else if(!mR1){
                start1();
            }
            b2.setBackgroundColor(Color.BLACK);
            b2.setTextColor(Color.WHITE);
            b1.setBackgroundColor(Color.WHITE);
            b1.setTextColor(Color.BLACK);
        });

        //When reset button is clicked
        reset.setOnClickListener(v -> reset());

        //When pause/resume button is clicked
        pause.setOnClickListener(v -> {
            if(mR1||mR2){
                pause();
            }
            else{
                resume();
            }
        });

        //When home button is clicked
        home.setOnClickListener(v -> {
            Intent gohome = new Intent(PlayerActivity.this,MainActivity.class);
            startActivity(gohome);
        });
    }

    //Pause method for button1
    private void pause1() {
        mCountDownTimer1.cancel();
        mTimeLeftInMillis1+=5000;
        updateb1();
        mR1=false;
    }

    //Pause method for button2
    private void pause2() {
        mCountDownTimer2.cancel();
        mTimeLeftInMillis2+=5000;
        updateb2();
        mR2=false;
    }

    //Start method for button1
    private void start1() {
        mCountDownTimer1 = new CountDownTimer(mTimeLeftInMillis1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis1 = millisUntilFinished;
                updateb1();
            }

            @Override
            public void onFinish() {
                mR1=false;
                mR2=false;
                reset.setVisibility(View.VISIBLE);
                b1.setBackgroundColor(Color.RED);
            }
        }.start();

        mR1=true;
        pause.setText("Pause");
        reset.setVisibility(View.INVISIBLE);
        updateb1();
    }

    //Start method for button2
    private void start2() {
        mCountDownTimer2 = new CountDownTimer(mTimeLeftInMillis2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis2=millisUntilFinished;
                updateb2();
            }

            @Override
            public void onFinish() {
                mR2=false;
                mR1=false;
                reset.setVisibility(View.VISIBLE);
                b2.setBackgroundColor(Color.RED);
            }
        }.start();
        mR2=true;
        pause.setText("Pause");
        reset.setVisibility(View.INVISIBLE);
        updateb2();
    }

    //Updating button1 text
    private void updateb1() {
        int minutes = (int) mTimeLeftInMillis1/1000/60;
        int seconds = (int) mTimeLeftInMillis1/1000 % 60;

        String TimeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        b1.setText(TimeFormatted);
    }

    //Updating button2 text
    private void updateb2() {
        int minutes = (int) mTimeLeftInMillis2/1000/60;
        int seconds = (int) mTimeLeftInMillis2/1000 % 60;

        String TimeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        b2.setText(TimeFormatted);
    }

    //Reset method
    private void reset(){
        mTimeLeftInMillis1=START_TIME_IN_MILLIS_1;
        mTimeLeftInMillis2=START_TIME_IN_MILLIS_2;
        updateb1();
        updateb2();
        reset.setVisibility(View.INVISIBLE);
        b1.setBackgroundColor(Color.BLACK);
        b2.setBackgroundColor(Color.BLACK);
        b1.setTextColor(Color.WHITE);
        b2.setTextColor(Color.WHITE);
    }

    //Pause method
    private void pause(){
        if(mR1){
            mCountDownTimer1.cancel();
            mR1=false;
            key=1;
        }
        if(mR2){
            mCountDownTimer2.cancel();
            mR2=false;
            key=2;
        }
        pause.setText("Resume");
        reset.setVisibility(View.VISIBLE);
    }

    //Resume method
    private void resume(){
        if(key==1){
            start1();
        }
        else{
            start2();
        }
        key=0;
        pause.setText("Pause");
        reset.setVisibility(View.INVISIBLE);
    }
}