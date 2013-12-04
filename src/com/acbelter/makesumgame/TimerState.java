package com.acbelter.makesumgame;

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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(millsUntilFinished);
        dest.writeByte((byte) (blink ? 1 : 0));
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
