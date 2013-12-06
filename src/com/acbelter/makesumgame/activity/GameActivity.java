package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.BlinkedCountDownTimer;
import com.acbelter.makesumgame.R.id;
import com.acbelter.makesumgame.R.layout;
import com.acbelter.makesumgame.TimerState;
import com.acbelter.makesumgame.game.*;
import com.acbelter.makesumgame.game.FieldGenerator.Level;

public class GameActivity extends Activity {
    private static final String KEY_TIMER_STATE =
            "com.acbelter.makesumgame.KEY_TIMER_STATE";
    private static final String KEY_GAME_OVER_FLAG =
            "com.acbelter.makesumgame.KEY_GAME_OVER_FLAG";
    private static final int FIELD_SIZE = 4;

    private Button[][] mFieldButtons;
    private TextView mPlayerSumView;
    private TextView mFullSumView;
    private TextView mScoreView;
    private TextView mTimerView;
    private BlinkedCountDownTimer mTimer;
    private TimerState mCurrentTimerState;
    private BaseGameScenario mGameScenario;
    private GameState mGameState;
    private boolean mGameOverFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game);
        findViews();
        mGameScenario = new SimpleGameScenario();
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
                setFieldClickable(false);
            }
            mCurrentTimerState = savedInstanceState.getParcelable(KEY_TIMER_STATE);
        } else {
            newGame(mGameScenario.getScene(0).level);
        }

        initFieldButtonsListeners();
    }

    private void findViews() {
        mPlayerSumView = (TextView) findViewById(id.player_sum);
        mFullSumView = (TextView) findViewById(id.full_sum);

        mFieldButtons = new Button[FIELD_SIZE][FIELD_SIZE];
        mFieldButtons[0][0] = (Button) findViewById(id.btn_0_0);
        mFieldButtons[0][1] = (Button) findViewById(id.btn_0_1);
        mFieldButtons[0][2] = (Button) findViewById(id.btn_0_2);
        mFieldButtons[0][3] = (Button) findViewById(id.btn_0_3);

        mFieldButtons[1][0] = (Button) findViewById(id.btn_1_0);
        mFieldButtons[1][1] = (Button) findViewById(id.btn_1_1);
        mFieldButtons[1][2] = (Button) findViewById(id.btn_1_2);
        mFieldButtons[1][3] = (Button) findViewById(id.btn_1_3);

        mFieldButtons[2][0] = (Button) findViewById(id.btn_2_0);
        mFieldButtons[2][1] = (Button) findViewById(id.btn_2_1);
        mFieldButtons[2][2] = (Button) findViewById(id.btn_2_2);
        mFieldButtons[2][3] = (Button) findViewById(id.btn_2_3);

        mFieldButtons[3][0] = (Button) findViewById(id.btn_3_0);
        mFieldButtons[3][1] = (Button) findViewById(id.btn_3_1);
        mFieldButtons[3][2] = (Button) findViewById(id.btn_3_2);
        mFieldButtons[3][3] = (Button) findViewById(id.btn_3_3);

        mScoreView = (TextView) findViewById(id.score);
        mTimerView = (TextView) findViewById(id.timer);
    }

    private void initButtonsState(boolean[][] state) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setSelected(state[i][j]);
            }
        }
    }

    private int[][] newField(Level level) {
        FieldGenerator.setLevel(level);
        int[][] field = FieldGenerator.generateNewField(FIELD_SIZE);
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
        if (mCurrentTimerState != null) {
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mCurrentTimerState.millsUntilFinished, 10*1000);
            mTimer.setBlink(mCurrentTimerState.blink);
            mTimer.start();
        } else if (!mGameOverFlag) {
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mGameScenario.getScene(mGameState.sceneNumber).timerMillis, 10*1000);
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
                        if (!v.isSelected()) {
                            v.setSelected(true);
                            mGameState.buttonsState[finalI][finalJ] = true;
                            mGameState.playerSum += mGameState.fieldNumbers[finalI][finalJ];
                        } else {
                            v.setSelected(false);
                            mGameState.buttonsState[finalI][finalJ] = false;
                            mGameState.playerSum -= mGameState.fieldNumbers[finalI][finalJ];
                            mGameState.score -=
                                    mGameScenario.getScene(mGameState.sceneNumber).undoPenalty;
                            if (mGameState.score < 0) {
                                mGameState.score = 0;
                            }
                            mScoreView.setText(mGameState.getScoreValue());
                        }
                        mPlayerSumView.setText(mGameState.getPlayerSumValue());
                        if (mGameState.playerSum == mGameState.fullSum) {
                            mGameState.score +=
                                    mGameScenario.getScene(mGameState.sceneNumber).madeSumScore;
                            mScoreView.setText(mGameState.getScoreValue());
                            if (mGameState.sceneNumber == mGameScenario.size()-1) {
                                endGame();
                            } else {
                                showMadeSumMessage();
                                newGame(mGameScenario.getScene(++mGameState.sceneNumber).level);
                            }
                        }
                    }
                });
            }
        }
    }

    private void endGame() {
        showGameOverMessage();
        mTimer.cancel();
        mTimerView.setText("00:00");
        setFieldClickable(false);
        mGameOverFlag = true;
    }

    private void newGame(Level level) {
        Log.d("DEBUG", "Level: " + level.name());
        int[][] field = newField(level);
        int fullSum = FieldGenerator.getRandomSum(field);
        mGameState = new GameState(field, 0, fullSum);

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
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mGameScenario.getScene(mGameState.sceneNumber).timerMillis, 10*1000);
            mTimer.start();
        }
    }

    private void showGameOverMessage() {
        Toast.makeText(this, "Game over!", Toast.LENGTH_LONG).show();
    }

    private void showMadeSumMessage() {
        Toast.makeText(this, "Made sum " + mGameState.getFullSumValue() + "!",
                Toast.LENGTH_LONG).show();
    }

    private void showLoseMessage() {
        Toast.makeText(this, "You lose!", Toast.LENGTH_LONG).show();
    }

    private void setFieldClickable(boolean clickable) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setClickable(clickable);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BaseGameState.KEY_GAME_STATE, mGameState);
        outState.putBoolean(KEY_GAME_OVER_FLAG, mGameOverFlag);
        if (!mGameOverFlag) {
            outState.putParcelable(KEY_TIMER_STATE, mTimer.getCurrentState());
        }
    }
}
