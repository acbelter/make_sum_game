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
import com.acbelter.makesumgame.Utils;

public class TrainingGameState extends BaseGameState {
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

    public String getFieldValue(int i, int j) {
        return String.valueOf(fieldNumbers[i][j]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeIntArray(Utils.toOneDimensionArray(fieldNumbers));
        out.writeInt(playerSum);
        out.writeInt(fullSum);
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
