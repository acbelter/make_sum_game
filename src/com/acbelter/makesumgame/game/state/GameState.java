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
    private Level level;
    private TimerState timerState;
    public long score;
    private int sceneIndex;

    public GameState(int[][] fieldNumbers, int playerSum, int fullSum,
                     Level level, int sceneIndex) {
        super(fieldNumbers, playerSum, fullSum);
        this.level = level;
        this.sceneIndex = sceneIndex;
    }

    public GameState(Parcel in) {
        super(in);
        level = in.readParcelable(Level.class.getClassLoader());
        timerState = in.readParcelable(TimerState.class.getClassLoader());
        score = in.readLong();
        sceneIndex = in.readInt();
    }

    public Level getLevel() {
        return level;
    }

    public TimerState getTimerState() {
        return timerState;
    }

    public int getSceneIndex() {
        return sceneIndex;
    }

    public void setTimerState(TimerState timerState) {
        this.timerState = timerState;
    }

    public void setSceneIndex(int sceneIndex) {
        this.sceneIndex = sceneIndex;
    }

    public String getScoreValue() {
        return String.valueOf(score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(level, flags);
        out.writeParcelable(timerState, flags);
        out.writeLong(score);
        out.writeInt(sceneIndex);
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
