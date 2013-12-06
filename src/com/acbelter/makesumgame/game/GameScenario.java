package com.acbelter.makesumgame.game;

import com.acbelter.makesumgame.game.FieldGenerator.Level;

import java.util.ArrayList;

public abstract class GameScenario {
    protected ArrayList<GameScene> mGameScenes;

    public GameScenario() {
        mGameScenes = new ArrayList<GameScene>();
        init();
    }

    public class GameScene  {
        public Level level;
        public long timerMillis;
        public int undoPenalty;
        public int madeSumScore;

        public GameScene() {}

        public GameScene(Level level, long timerMillis, int undoPenalty, int madeSumScore) {
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

    public void addScene(Level level, long timerMillis, int undoPenalty, int madeSumScore) {
        mGameScenes.add(new GameScene(level, timerMillis, undoPenalty, madeSumScore));
    }

}
