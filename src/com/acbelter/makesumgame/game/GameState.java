package com.acbelter.makesumgame.game;

import android.os.Parcel;
import android.os.Parcelable;

public class GameState extends TrainingGameState {
    public long score;
    public int sceneNumber;

    public GameState(int[][] fieldNumbers, int playerSum, int fullSum) {
        super(fieldNumbers, playerSum, fullSum);
    }

    public GameState(Parcel in) {
        super(in);
        score = in.readLong();
        sceneNumber = in.readInt();
    }

    public String getScoreValue() {
        return String.valueOf(score);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(score);
        dest.writeInt(sceneNumber);
    }

    public static final Parcelable.Creator<GameState> CREATOR =
            new Parcelable.Creator<GameState>() {

                @Override
                public GameState createFromParcel(Parcel source) {
                    return new GameState(source);
                }

                @Override
                public GameState[] newArray(int size) {
                    return new GameState[size];
                }
            };
}
