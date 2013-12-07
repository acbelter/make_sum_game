package com.acbelter.makesumgame.game;

import com.acbelter.makesumgame.game.FieldGenerator.Level;

public class SimpleGameScenario extends BaseGameScenario {
    @Override
    public void init() {
        for (int i = 0; i < 5; i++) {
            addScene(Level.EASY, 31*1000, 1, 2);
        }
        for (int i = 0; i < 5; i++) {
            addScene(Level.MEDIUM, 31*1000, 3, 6);
        }
        for (int i = 0; i < 5; i++) {
            addScene(Level.HARD, 31*1000, 5, 10);
        }
    }
}
