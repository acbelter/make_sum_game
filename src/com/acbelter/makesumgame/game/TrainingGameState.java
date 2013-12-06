package com.acbelter.makesumgame.game;

import android.os.Parcel;
import android.os.Parcelable;
import com.acbelter.makesumgame.Utils;

public class TrainingGameState extends GameState {
    public int[][] fieldNumbers;
    public boolean[][] buttonsState;
    public int playerSum;
    public int fullSum;

    public TrainingGameState(int[][] fieldNumbers, int playerSum, int fullSum) {
        this.fieldNumbers = fieldNumbers;
        int size = Utils.getDimension(fieldNumbers);
        buttonsState = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttonsState[i][j] = false;
            }
        }
        this.playerSum = playerSum;
        this.fullSum = fullSum;
    }

    public TrainingGameState(Parcel in) {
        int[] values = in.createIntArray();
        fieldNumbers = Utils.toTwoDimensionArray(values);
        playerSum = in.readInt();
        fullSum = in.readInt();
    }

    public String getPlayerSumValue() {
        return String.valueOf(playerSum);
    }

    public String getFullSumValue() {
        return String.valueOf(fullSum);
    }

    public String getFieldNumberValue(int i, int j) {
        return String.valueOf(fieldNumbers[i][j]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(Utils.toOneDimensionArray(fieldNumbers));
        dest.writeInt(playerSum);
        dest.writeInt(fullSum);
    }

    public static final Parcelable.Creator<TrainingGameState> CREATOR =
            new Parcelable.Creator<TrainingGameState>() {

                @Override
                public TrainingGameState createFromParcel(Parcel source) {
                    return new TrainingGameState(source);
                }

                @Override
                public TrainingGameState[] newArray(int size) {
                    return new TrainingGameState[size];
                }
            };
}
