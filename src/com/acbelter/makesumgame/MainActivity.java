package com.acbelter.makesumgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent startGameIntent = new Intent(this, GameActivity.class);
        startActivity(startGameIntent);
    }

    public void startChallenge(View view) {

    }

    public void showHighScore(View view) {

    }

    public void showSettings(View view) {

    }

    public void exit(View view) {
        finish();
    }
}
