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

package com.acbelter.makesumgame.game;

import com.acbelter.makesumgame.game.FieldGenerator.Level;

import java.util.ArrayList;

public abstract class BaseGameScenario {
    protected ArrayList<GameScene> mGameScenes;

    public BaseGameScenario() {
        mGameScenes = new ArrayList<GameScene>();
        init();
    }

    public class GameScene  {
        public Level level;
        public long timerMillis;
        public long undoPenalty;
        public long madeSumScore;

        public GameScene(Level level, long timerMillis, long undoPenalty, long madeSumScore) {
            this.level = level;
            this.timerMillis = timerMillis;
            this.undoPenalty = undoPenalty;
            this.madeSumScore = madeSumScore;
        }
    }

    public abstract void init();

    public GameScene getScene(int location) {
        return mGameScenes.get(location);
    }

    public int size() {
        return mGameScenes.size();
    }

    public void addScene(Level level, long timerMillis, long undoPenalty, long madeSumScore) {
        mGameScenes.add(new GameScene(level, timerMillis, undoPenalty, madeSumScore));
    }
}
