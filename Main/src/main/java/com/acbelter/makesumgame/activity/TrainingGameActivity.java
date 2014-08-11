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
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.R;
import com.acbelter.makesumgame.game.Difficulty;
import com.acbelter.makesumgame.game.GameFieldGenerator;
import com.acbelter.makesumgame.game.state.BaseGameState;
import com.acbelter.makesumgame.game.state.TrainingGameState;

public class TrainingGameActivity extends Activity {
    private static final int FIELD_SIZE = 4;
    private TrainingGameState mGameState;
    private Button[][] mFieldButtons;
    private TextView mPlayerSumView;
    private TextView mFullSumView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_game);
        findViews();
        if (savedInstanceState != null) {
            mGameState = savedInstanceState.getParcelable(BaseGameState.KEY_GAME_STATE);
            mPlayerSumView.setText(mGameState.getPlayerSumValue());
            mFullSumView.setText(mGameState.getFullSumValue());
            initField(mGameState.fieldNumbers);
            initButtonsState(mGameState.buttonsState);
        } else {
            newGame(loadLevelFromPref());
        }
        initFieldButtonsListeners();
    }

    private Difficulty loadLevelFromPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String levelStr = prefs.getString(SettingsActivity.PREF_TRAINING_DIFFICULTY, Difficulty.EASY.name());
        return Difficulty.valueOf(levelStr);
    }

    private void findViews() {
        mPlayerSumView = (TextView) findViewById(R.id.player_sum);
        mFullSumView = (TextView) findViewById(R.id.full_sum);

        mFieldButtons = new Button[FIELD_SIZE][FIELD_SIZE];
        mFieldButtons[0][0] = (Button) findViewById(R.id.btn_0_0);
        mFieldButtons[0][1] = (Button) findViewById(R.id.btn_0_1);
        mFieldButtons[0][2] = (Button) findViewById(R.id.btn_0_2);
        mFieldButtons[0][3] = (Button) findViewById(R.id.btn_0_3);

        mFieldButtons[1][0] = (Button) findViewById(R.id.btn_1_0);
        mFieldButtons[1][1] = (Button) findViewById(R.id.btn_1_1);
        mFieldButtons[1][2] = (Button) findViewById(R.id.btn_1_2);
        mFieldButtons[1][3] = (Button) findViewById(R.id.btn_1_3);

        mFieldButtons[2][0] = (Button) findViewById(R.id.btn_2_0);
        mFieldButtons[2][1] = (Button) findViewById(R.id.btn_2_1);
        mFieldButtons[2][2] = (Button) findViewById(R.id.btn_2_2);
        mFieldButtons[2][3] = (Button) findViewById(R.id.btn_2_3);

        mFieldButtons[3][0] = (Button) findViewById(R.id.btn_3_0);
        mFieldButtons[3][1] = (Button) findViewById(R.id.btn_3_1);
        mFieldButtons[3][2] = (Button) findViewById(R.id.btn_3_2);
        mFieldButtons[3][3] = (Button) findViewById(R. id.btn_3_3);
    }

    private void initFieldButtonsListeners() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                final int finalI = i;
                final int finalJ = j;
                mFieldButtons[i][j].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!v.isSelected()) {
                            v.setSelected(true);
                            mGameState.buttonsState[finalI][finalJ] = true;
                            mGameState.playerSum += mGameState.fieldNumbers[finalI][finalJ];
                        } else {
                            v.setSelected(false);
                            mGameState.buttonsState[finalI][finalJ] = false;
                            mGameState.playerSum -= mGameState.fieldNumbers[finalI][finalJ];
                        }
                        mPlayerSumView.setText(mGameState.getPlayerSumValue());
                        if (mGameState.playerSum == mGameState.fullSum) {
                            showMadeSumMessage();
                            newGame(loadLevelFromPref());
                        }
                    }
                });
            }
        }
    }

    private void newGame(Difficulty difficulty) {
        int[][] field = newField(difficulty);
        int fullSum = GameFieldGenerator.getRandomSum(field);
        if (mGameState == null) {
            mGameState = new TrainingGameState(field, 0, fullSum);
        } else {
            mGameState.fieldNumbers = field;
            mGameState.playerSum = 0;
            mGameState.fullSum = fullSum;
        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setSelected(false);
            }
        }

        mPlayerSumView.setText(mGameState.getPlayerSumValue());
        mFullSumView.setText(mGameState.getFullSumValue());
    }

    private void showMadeSumMessage() {
        Toast.makeText(this, "Made sum " + mGameState.getFullSumValue() + "!",
                Toast.LENGTH_SHORT).show();
    }

    private void initButtonsState(boolean[][] state) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setSelected(state[i][j]);
            }
        }
    }

    private int[][] newField(Difficulty difficulty) {
        int[][] field = GameFieldGenerator.generateNewField(FIELD_SIZE, difficulty);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(field[i][j]));
            }
        }
        return field;
    }

    private void initField(int[][] fieldNumbers) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(fieldNumbers[i][j]));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BaseGameState.KEY_GAME_STATE, mGameState);
    }
}
