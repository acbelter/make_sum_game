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

package com.acbelter.makesumgame.game.state;

import android.os.Parcel;
import android.os.Parcelable;

public class TimerState implements Parcelable {
    public long millsUntilFinished;
    public boolean blink;

    public TimerState(long millsUntilFinished, boolean blink) {
        this.millsUntilFinished = millsUntilFinished;
        this.blink = blink;
    }

    public TimerState(Parcel inParcel) {
        millsUntilFinished = inParcel.readLong();
        blink = inParcel.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(millsUntilFinished);
        out.writeByte((byte) (blink ? 1 : 0));
    }

    public static final Parcelable.Creator<TimerState> CREATOR =
            new Parcelable.Creator<TimerState>() {

                @Override
                public TimerState createFromParcel(Parcel source) {
                    return new TimerState(source);
                }

                @Override
                public TimerState[] newArray(int size) {
                    return new TimerState[size];
                }
            };
}
