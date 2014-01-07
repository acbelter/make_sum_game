package com.acbelter.makesumgame.activity;

import com.acbelter.makesumgame.game.Level;

public class LevelItem {
    private Level mLevel;
    public int levelNumber;
    public long maxScore;
    public boolean levelLock;

    public LevelItem(Level level) {
        mLevel = level;
        levelNumber = level.getId();
    }

    public Level getLevel() {
        return mLevel;
    }
}
