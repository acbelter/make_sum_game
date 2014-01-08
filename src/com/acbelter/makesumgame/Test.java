package com.acbelter.makesumgame;

import com.acbelter.makesumgame.game.Difficulty;
import com.acbelter.makesumgame.game.Level;
import com.acbelter.makesumgame.game.Scene;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static ArrayList<Level> generateTestLevels() {
        ArrayList<Level> levels = new ArrayList<Level>();
        Level level = new Level(1, 10);
        List<Scene> scenes = new ArrayList<Scene>();
        scenes.add(new Scene(Difficulty.EASY, 30, 10, 50));
//        scenes.add(new Scene(Difficulty.MEDIUM, 30, 20, 30));
//        scenes.add(new Scene(Difficulty.HARD, 30, 30, 40));
        level.setLevelScenes(scenes);
        levels.add(level);
        return levels;
    }
}
