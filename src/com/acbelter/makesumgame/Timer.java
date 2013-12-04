package com.acbelter.makesumgame;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class Timer {
    private TextView mTimerView;
    private Handler mTimerHandler;
    private Runnable updateTimerMethod;
    private long mStartTime;
    private long mTimeInMills;
    private long mTimeSwap;
    private long mFinalTime;

    public Timer(TextView timerView) {
        mTimerHandler = new Handler();
        mTimerView = timerView;
        updateTimerMethod = new Runnable() {
            @Override
            public void run() {
                mTimeInMills = SystemClock.uptimeMillis() - mStartTime;
                mFinalTime = mTimeSwap + mTimeInMills;
                int seconds = (int) (mFinalTime/1000);
                int minutes = seconds/60;
                seconds = seconds%60;
                if (seconds < 10) {
                    mTimerView.setText(minutes + ":0" + seconds);
                } else {
                    mTimerView.setText(minutes + ":" + seconds);
                }
                mTimerHandler.postDelayed(this, 0);
            }
        };
    }

    public void start() {
        mStartTime = SystemClock.uptimeMillis();
        mTimerHandler.postDelayed(updateTimerMethod, 0);
    }

    public void pause() {
        mTimeSwap += mTimeInMills;
        mTimerHandler.removeCallbacks(updateTimerMethod);
    }

    public void stop() {

    }
}
