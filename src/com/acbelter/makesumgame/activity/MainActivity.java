package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.acbelter.makesumgame.R.layout;
import com.acbelter.makesumgame.game.FieldGenerator.Level;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.contains(SettingsActivity.PREF_LEVEL)) {
            Editor editor = prefs.edit();
            editor.putString(SettingsActivity.PREF_LEVEL, Level.EASY.name());
            editor.commit();
        }
    }

    public void startGame(View view) {
        Intent startIntent = new Intent(this, GameActivity.class);
        startActivity(startIntent);
    }

    public void startTraining(View view) {
        Intent startIntent = new Intent(this, TrainingGameActivity.class);
        startActivity(startIntent);
    }

    public void openHighscores(View view) {

    }

    public void openSettings(View view) {
        Intent startIntent = new Intent(this, SettingsActivity.class);
        startActivity(startIntent);
    }

    public void exit(View view) {
        finish();
    }
}
