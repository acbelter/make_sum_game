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
import com.acbelter.makesumgame.game.Level;

public class GameState extends TrainingGameState {
    public Level level;
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
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeLong(score);
        out.writeInt(sceneNumber);
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
