/*
 * Copyright 2014 acbelter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public ColorStateList getDefaultTimerViewColor() {
        return mDefaultTimerViewColor;
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
