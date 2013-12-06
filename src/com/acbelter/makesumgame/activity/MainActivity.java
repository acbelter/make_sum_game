package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.acbelter.makesumgame.R;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }

    public void exit(View view) {
        finish();
    }
}
