package com.acbelter.makesumgame;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

public class BlinkedCountDownTimer extends CountDownTimer {
    protected TextView mTimerView;
    protected static int sBlinkColor = Color.RED;

    private ColorStateList mDefaultTimerViewColor;
    private long mTimeBlinkInMillis;      // start time of start blinking
    private boolean mBlink;               // controls the blinking .. on and off
    private long mMillisUntilFinished;

    public BlinkedCountDownTimer(TextView timerView,
                                 long totalTimeCount,
                                 long timeBlink) {
        super(totalTimeCount, 500);
        mTimeBlinkInMillis = timeBlink;
        mTimerView = timerView;
        mDefaultTimerViewColor = mTimerView.getTextColors();
    }

    public void setBlink(boolean blink) {
        mBlink = blink;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mMillisUntilFinished = millisUntilFinished;
        if (millisUntilFinished < mTimeBlinkInMillis) {
            if (mBlink) {
                mTimerView.setTextColor(sBlinkColor);
            } else {
                mTimerView.setTextColor(mDefaultTimerViewColor);
            }
            mBlink = !mBlink;
        }

        long seconds = millisUntilFinished/1000;
        long minutes = seconds/60;
        seconds = seconds%60;
        mTimerView.setText(String.format("%02d", minutes) +
                ":" + String.format("%02d", seconds));
    }

    @Override
    public void onFinish() {
//        mTimerView.setTextColor(sBlinkColor);
    }

    public TimerState getCurrentState() {
        if (mMillisUntilFinished == 0) {
            return null;
        }
        return new TimerState(mMillisUntilFinished, mBlink);
    }
}
