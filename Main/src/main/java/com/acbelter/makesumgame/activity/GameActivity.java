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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.R;
import com.acbelter.makesumgame.Utils;
import com.acbelter.makesumgame.game.*;
import com.acbelter.makesumgame.game.state.BaseGameState;
import com.acbelter.makesumgame.game.state.GameState;
import com.acbelter.makesumgame.game.state.TimerState;

public class GameActivity extends Activity {
    private static final String KEY_GAME_OVER_FLAG =
            "com.acbelter.makesumgame.KEY_GAME_OVER_FLAG";
    public static final String KEY_LEVEL =
            "com.acbelter.makesumgame.KEY_LEVEL";
    public static final String KEY_SCORE =
            "com.acbelter.makesumgame.KEY_SCORE";
    private static final int FIELD_SIZE = 4;
    private static final long TIME_BLINK = 10000L;
    private static final int LEVEL_FINISHED = 0;
    private static final int LEVEL_NOT_FINISHED = 1;

    private Button[][] mFieldButtons;
    private TextView mPlayerSumView;
    private TextView mFullSumView;
    private TextView mScoreView;
    private TextView mTimerView;
    private BlinkedCountDownTimer mTimer;
    private GameState mGameState;
    private boolean mGameOverFlag;
    private Handler mTimerHandler;
    private Level mLevel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mPlayerSumView = (TextView) findViewById(R.id.player_sum);
        mFullSumView = (TextView) findViewById(R.id.full_sum);
        mScoreView = (TextView) findViewById(R.id.score);
        mTimerView = (TextView) findViewById(R.id.timer);
        findFieldViews();

        mTimerHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == BlinkedCountDownTimer.CODE_FINISHED) {
                    showLevelNotCompleteMessage();
                    finishLevel(LEVEL_NOT_FINISHED);
                }
            }
        };

        if (savedInstanceState != null) {
            mGameState = savedInstanceState.getParcelable(BaseGameState.KEY_GAME_STATE);
            mPlayerSumView.setText(mGameState.getPlayerSumValue());
            mFullSumView.setText(mGameState.getFullSumValue());
            mScoreView.setText(mGameState.getScoreValue());
            initField(mGameState.fieldNumbers);
            mGameOverFlag = savedInstanceState.getBoolean(KEY_GAME_OVER_FLAG);
            if (!mGameOverFlag) {
                initButtonsState(mGameState.buttonsState);
            } else {
                setFieldEnabled(false);
            }
        } else {
            mLevel = getIntent().getParcelableExtra(SelectLevelActivity.KEY_SELECTED_LEVEL);
            newScene(mLevel, 0);
        }

        initFieldButtonsListeners();
    }

    private void findFieldViews() {
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
        mFieldButtons[3][3] = (Button) findViewById(R.id.btn_3_3);
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
                mFieldButtons[i][j].setClickable(true);
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
    protected void onResume() {
        super.onResume();
        TimerState timerState = mGameState.getTimerState();
        if (timerState != null) {
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    timerState.millsUntilFinished, TIME_BLINK, mTimerHandler);
            mTimer.setBlink(timerState.blink);
            mTimer.start();
        } else if (!mGameOverFlag) {
            int sceneIndex = mGameState.getSceneIndex();
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mLevel.getSceneWithIndex(sceneIndex).getTimerMillis(),
                    TIME_BLINK, mTimerHandler);
            mTimer.start();
        }
    }

    private void initFieldButtonsListeners() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                final int finalI = i;
                final int finalJ = j;
                mFieldButtons[i][j].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int sceneIndex = mGameState.getSceneIndex();
                        if (!v.isSelected()) {
                            v.setSelected(true);
                            mGameState.buttonsState[finalI][finalJ] = true;
                            mGameState.playerSum += mGameState.fieldNumbers[finalI][finalJ];
                        } else {
                            v.setSelected(false);
                            mGameState.buttonsState[finalI][finalJ] = false;
                            mGameState.playerSum -= mGameState.fieldNumbers[finalI][finalJ];
                            mGameState.score -=
                                    mLevel.getSceneWithIndex(sceneIndex).getUndoPenalty();
                            if (mGameState.score < 0) {
                                mGameState.score = 0;
                            }
                            mScoreView.setText(mGameState.getScoreValue());
                        }
                        mPlayerSumView.setText(mGameState.getPlayerSumValue());
                        if (mGameState.playerSum == mGameState.fullSum) {
                            mGameState.score +=
                                    mLevel.getSceneWithIndex(sceneIndex).getMadeSumScore();
                            mScoreView.setText(mGameState.getScoreValue());
                            if (sceneIndex == mLevel.getLevelScenes().size()-1) {
                                showLevelCompleteMessage();
                                finishLevel(LEVEL_FINISHED);
                            } else {
                                showMadeSumMessage();
                                mGameState.setSceneIndex(++sceneIndex);
                                newScene(mLevel, sceneIndex);
                            }
                        }
                    }
                });
            }
        }
    }

    private void finishLevel(int resultKey) {
        mTimer.cancel();
        mTimerView.setText("00:00");
        setFieldEnabled(false);
        mGameOverFlag = true;
        if (resultKey == LEVEL_FINISHED) {
            Intent intent = new Intent();
            intent.putExtra(KEY_LEVEL, mLevel);
            intent.putExtra(KEY_SCORE, mGameState.score);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_OK);
        }
        finish();
    }

    private boolean newScene(Level level, int sceneIndex) {
        if (Utils.DEBUG_MODE) {
            Log.d(Utils.DEBUG_TAG, "Level: " + level.getId() + ". Scene index: " + sceneIndex);
        }

        Scene scene = level.getSceneWithIndex(sceneIndex);
        if (scene == null) {
            return false;
        }

        int[][] field = newField(scene.getDifficulty());
        int fullSum = GameFieldGenerator.getRandomSum(field);
        if (mGameState == null) {
            mGameState = new GameState(field, 0, fullSum, mLevel, sceneIndex);
        } else {
            mGameState.fieldNumbers = field;
            mGameState.playerSum = 0;
            mGameState.fullSum = fullSum;
            mGameState.setSceneIndex(sceneIndex);
        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setSelected(false);
            }
        }

        mPlayerSumView.setText(mGameState.getPlayerSumValue());
        mFullSumView.setText(mGameState.getFullSumValue());
        mScoreView.setText(mGameState.getScoreValue());
        mGameOverFlag = false;

        if (mTimer != null) {
            mTimer.cancel();
            mTimerView.setTextColor(mTimer.getDefaultTimerViewColor());
            int index = mGameState.getSceneIndex();
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mLevel.getSceneWithIndex(index).getTimerMillis(),
                    TIME_BLINK, mTimerHandler);
            mTimer.start();
        }
        return true;
    }

    private void showMadeSumMessage() {
        Toast.makeText(this, getResources().getString(R.string.made_sum_msg)
                + " " + mGameState.getFullSumValue() + "!", Toast.LENGTH_SHORT).show();
    }

    private void showLevelCompleteMessage() {
        Toast.makeText(this, getResources().getString(R.string.is_completed)
                + " " + mGameState.score + ".", Toast.LENGTH_LONG).show();
    }

    private void showLevelNotCompleteMessage() {
        Toast.makeText(this, getResources().getString(R.string.is_not_completed),
                Toast.LENGTH_LONG).show();
    }

    private void setFieldEnabled(boolean enabled) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setEnabled(enabled);
            }
        }
    }

//    private void setFieldEmpty(boolean empty) {
//        if (Utils.DEBUG_MODE) {
//            Log.d(Utils.DEBUG_TAG, "Set game_field empty: " + empty);
//        }
//
//        for (int i = 0; i < FIELD_SIZE; i++) {
//            for (int j = 0; j < FIELD_SIZE; j++) {
//                if (empty) {
//                    mFieldButtons[i][j].setText("");
//                } else {
//                    mFieldButtons[i][j].setText(mGameState.getFieldValue(i, j));
//                }
//            }
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!mGameOverFlag) {
            mGameState.setTimerState(mTimer.getCurrentState());
        }
        outState.putParcelable(BaseGameState.KEY_GAME_STATE, mGameState);
        outState.putBoolean(KEY_GAME_OVER_FLAG, mGameOverFlag);

        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
