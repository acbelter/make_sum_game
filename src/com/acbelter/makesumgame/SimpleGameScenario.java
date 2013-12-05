package com.acbelter.makesumgame;

import com.acbelter.makesumgame.FieldGenerator.Level;

public class SimpleGameScenario extends GameScenario {
    public SimpleGameScenario(int undoPenalty, int madeSumScore) {
        super(undoPenalty, madeSumScore);
    }

    @Override
    public void init() {
        addScene(Level.EASY, 5, 30*1000);
        addScene(Level.MEDIUM, 5, 30*1000);
        addScene(Level.HARD, 5, 30*1000);
    }
}
