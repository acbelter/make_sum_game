package com.acbelter.makesumgame.game;

import android.os.Parcel;
import android.os.Parcelable;

public class Scene implements Parcelable {
    private Difficulty mDifficulty;
    private int mTimerSeconds;
    private long mUndoPenalty;
    private long mMadeSumScore;

    public Scene() {}

    public Scene(Difficulty difficulty, int timerSeconds, long undoPenalty,
                 long madeSumScore) {
        mDifficulty = difficulty;
        mTimerSeconds = timerSeconds;
        mUndoPenalty = undoPenalty;
        mMadeSumScore = madeSumScore;
    }

    private Scene(Parcel in) {
        mDifficulty = Difficulty.valueOf(in.readString());
        mTimerSeconds = in.readInt();
        mUndoPenalty = in.readLong();
        mMadeSumScore = in.readLong();
    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }

    public int getTimerMillis() {
        return mTimerSeconds*1000;
    }

    public long getUndoPenalty() {
        return mUndoPenalty;
    }

    public long getMadeSumScore() {
        return mMadeSumScore;
    }

    public void setDifficulty(String difficulty) {
        mDifficulty = Difficulty.valueOf(difficulty.toUpperCase());
    }

    public void setDifficulty(Difficulty difficulty) {
        mDifficulty = difficulty;
    }

    public void setTimerSeconds(int timerSeconds) {
        mTimerSeconds = timerSeconds;
    }

    public void setUndoPenalty(long undoPenalty) {
        mUndoPenalty = undoPenalty;
    }

    public void setMadeSumScore(long madeSumScore) {
        mMadeSumScore = madeSumScore;
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
        out.writeString(mDifficulty.name());
        out.writeInt(mTimerSeconds);
        out.writeLong(mUndoPenalty);
        out.writeLong(mMadeSumScore);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("difficulty: ");
        builder.append(mDifficulty.name());
        builder.append("; ");
        builder.append("timer_seconds: ");
        builder.append(mTimerSeconds);
        builder.append("; ");
        builder.append("undo_penalty: ");
        builder.append(mUndoPenalty);
        builder.append("; ");
        builder.append("made_sum_score: ");
        builder.append(mMadeSumScore);
        return builder.toString();
    }
}
