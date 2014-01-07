package com.acbelter.makesumgame.game;

import android.os.Parcel;
import android.os.Parcelable;

public class Scene implements Parcelable {
    private int mId;
    private Difficulty mDifficulty;
    private int mTimerSeconds;
    private long mUndoPenalty;
    private long mMadeSumScore;

    public Scene(int id, Difficulty difficulty, int timerSeconds, long undoPenalty,
                 long madeSumScore) {
        mId = id;
        mDifficulty = difficulty;
        mTimerSeconds = timerSeconds;
        mUndoPenalty = undoPenalty;
        mMadeSumScore = madeSumScore;
    }

    private Scene(Parcel in) {
        mId = in.readInt();
        mDifficulty = Difficulty.valueOf(in.readString());
        mTimerSeconds = in.readInt();
        mUndoPenalty = in.readLong();
        mMadeSumScore = in.readLong();
    }

    public static final Parcelable.Creator<Scene> CREATOR =
            new Parcelable.Creator<Scene>() {

                @Override
                public Scene createFromParcel(Parcel source) {
                    return new Scene(source);
                }

                @Override
                public Scene[] newArray(int size) {
                    return new Scene[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mDifficulty.name());
        out.writeInt(mTimerSeconds);
        out.writeLong(mUndoPenalty);
        out.writeLong(mMadeSumScore);
    }
}
