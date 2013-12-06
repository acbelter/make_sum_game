package com.acbelter.makesumgame.game;

import com.acbelter.makesumgame.game.FieldGenerator.Level;

public class SimpleGameScenario extends GameScenario {
    private static final int UNDO_PENALTY = 10;
    private static final int MADE_SUM_SCORE = 20;

    public SimpleGameScenario() {
        super();
    }

    @Override
    public void init() {
        for (int i = 0; i < 1; i++) {
            addScene(Level.EASY, 30*1000, UNDO_PENALTY, MADE_SUM_SCORE);
        }
        for (int i = 0; i < 1; i++) {
            addScene(Level.MEDIUM, 30*1000, UNDO_PENALTY, MADE_SUM_SCORE);
        }
        for (int i = 0; i < 1; i++) {
            addScene(Level.HARD, 30*1000, UNDO_PENALTY, MADE_SUM_SCORE);
        }
    }
}
