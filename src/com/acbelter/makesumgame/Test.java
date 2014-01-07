package com.acbelter.makesumgame;

import com.acbelter.makesumgame.game.Difficulty;
import com.acbelter.makesumgame.game.Level;
import com.acbelter.makesumgame.game.Scene;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static List<Level> generateTestLevels() {
        List<Level> levels = new ArrayList<Level>();
        Level level = new Level(1, 1000);
        List<Scene> scenes = new ArrayList<Scene>();
        scenes.add(new Scene(1, Difficulty.EASY, 30, 10, 20));
        scenes.add(new Scene(2, Difficulty.MEDIUM, 30, 20, 30));
        scenes.add(new Scene(3, Difficulty.HARD, 30, 30, 40));
        level.setLevelScenes(scenes);
        levels.add(level);
        return levels;
    }
}
