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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioButton;
import com.acbelter.makesumgame.R;
import com.acbelter.makesumgame.game.Difficulty;

public class SettingsActivity extends Activity {
    public static final String PREF_LEVEL = "level";
    private SharedPreferences mPrefs;
    private RadioButton mRadioEasy;
    private RadioButton mRadioMedium;
    private RadioButton mRadioHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mRadioEasy = (RadioButton) findViewById(R.id.level_easy);
        mRadioMedium = (RadioButton) findViewById(R.id.level_medium);
        mRadioHard = (RadioButton) findViewById(R.id.level_hard);
        selectLevel(Difficulty.valueOf(mPrefs.getString(PREF_LEVEL, Difficulty.EASY.name())));
    }

    private void selectLevel(Difficulty difficulty) {
        switch (difficulty) {
            case EASY: {
                mRadioEasy.setChecked(true);
                break;
            }
            case MEDIUM: {
                mRadioMedium.setChecked(true);
                break;
            }
            case HARD: {
                mRadioHard.setChecked(true);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported level");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Editor editor = mPrefs.edit();
        if (mRadioEasy.isChecked()) {
            editor.putString(PREF_LEVEL, Difficulty.EASY.name());
        }
        if (mRadioMedium.isChecked()) {
            editor.putString(PREF_LEVEL, Difficulty.MEDIUM.name());
        }
        if (mRadioHard.isChecked()) {
            editor.putString(PREF_LEVEL, Difficulty.HARD.name());
        }
        editor.commit();
    }
}
