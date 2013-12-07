package com.acbelter.makesumgame;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class BlinkedCountDownTimer extends CountDownTimer {
    public static final int CODE_FINISHED = 0;
    private static int sBlinkColor = Color.RED;
    private TextView mTimerView;
    private ColorStateList mDefaultTimerViewColor;
    private long mTimeBlinkInMillis;
    private boolean mBlink;
    private long mMillisUntilFinished;
    private Handler mHandler;

    public BlinkedCountDownTimer(TextView timerView,
                                 long totalTimeCount,
                                 long timeBlink,
                                 Handler handler) {
        super(totalTimeCount, 500);
        mHandler = handler;
        mTimeBlinkInMillis = timeBlink;
        mTimerView = timerView;
        mDefaultTimerViewColor = mTimerView.getTextColors();
        mBlink = true;
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
        mTimerView.setTextColor(mDefaultTimerViewColor);
        mHandler.sendEmptyMessage(CODE_FINISHED);
    }

    public TimerState getCurrentState() {
        if (mMillisUntilFinished == 0) {
            return null;
        }
        return new TimerState(mMillisUntilFinished, mBlink);
    }
}
