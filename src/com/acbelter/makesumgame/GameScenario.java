package com.acbelter.makesumgame;

import com.acbelter.makesumgame.FieldGenerator.Level;

import java.util.ArrayList;

public abstract class GameScenario {
    protected int mUndoPenalty;
    protected int mMadeSumScore;
    protected ArrayList<GameScene> mGameScenes;

    public GameScenario(int undoPenalty, int madeSumScore) {
        mUndoPenalty = undoPenalty;
        mMadeSumScore = madeSumScore;
        mGameScenes = new ArrayList<GameScene>();
    }

    public class GameScene {
        public Level level;
        public int levelCount;
        public long timerMillis;

        public GameScene() {}

        public GameScene(Level level, int levelCount, long timerMillis) {
            this.level = level;
            this.levelCount = levelCount;
            this.timerMillis = timerMillis;
        }
    }

    public abstract void init();

    public GameScene getScene(int location) {
        return mGameScenes.get(location);
    }

    public void addScene(Level level, int levelCount, long timerMillis) {
        mGameScenes.add(new GameScene(level, levelCount, timerMillis));
    }

    public int getUndoPenalty() {
        return mUndoPenalty;
    }

    public void setUndoPenalty(int undoPenalty) {
        mUndoPenalty = undoPenalty;
    }

    public int getMadeSumScore() {
        return mMadeSumScore;
    }

    public void setMadeSumScore(int madeSumScore) {
        mMadeSumScore = madeSumScore;
    }
}
