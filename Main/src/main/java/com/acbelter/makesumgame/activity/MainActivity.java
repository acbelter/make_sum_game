/*
 * Copyright 2014 acbelter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.acbelter.makesumgame.R;
import com.acbelter.makesumgame.game.Difficulty;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.contains(TrainingSettingsActivity.PREF_TRAINING_DIFFICULTY)) {
            Editor editor = prefs.edit();
            editor.putString(TrainingSettingsActivity.PREF_TRAINING_DIFFICULTY, Difficulty.EASY.name());
            editor.commit();
        }
    }

    public void startGame(View view) {
        Intent startIntent = new Intent(this, SelectLevelActivity.class);
        startActivity(startIntent);
        overridePendingTransition(R.anim.enter_slide_in, R.anim.enter_slide_out);
    }

    public void startTraining(View view) {
        Intent startIntent = new Intent(this, TrainingSettingsActivity.class);
        startActivity(startIntent);
        overridePendingTransition(R.anim.enter_slide_in, R.anim.enter_slide_out);
    }

    public void openAbout(View view) {
        Intent startIntent = new Intent(this, AboutActivity.class);
        startActivity(startIntent);
        overridePendingTransition(R.anim.enter_slide_in, R.anim.enter_slide_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }

    public void exit(View view) {
        finish();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }
}
