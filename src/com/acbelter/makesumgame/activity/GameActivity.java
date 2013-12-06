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
import com.acbelter.makesumgame.Utils;
import com.acbelter.makesumgame.game.FieldGenerator;
import com.acbelter.makesumgame.game.FieldGenerator.Level;
import com.acbelter.makesumgame.game.GameScenario;
import com.acbelter.makesumgame.game.SimpleGameScenario;

import java.util.ArrayList;

public class GameActivity extends Activity {
    private static final String KEY_PLAYER_SUM =
            "com.acbelter.makesumgame.KEY_PLAYER_SUM";
    private static final String KEY_FULL_SUM =
            "com.acbelter.makesumgame.KEY_FULL_SUM";
    private static final String KEY_FIELD_NUMBERS =
            "com.acbelter.makesumgame.KEY_FIELD_NUMBERS";
    private static final String KEY_BUTTONS_STATE =
            "com.acbelter.makesumgame.KEY_BUTTONS_STATE";
    private static final String KEY_SCORE =
            "com.acbelter.makesumgame.KEY_SCORE";
    private static final String KEY_FIELD_CLICKABLE =
            "com.acbelter.makesumgame.KEY_FIELD_CLICKABLE";
    private static final String KEY_TIMER_STATE =
            "com.acbelter.makesumgame.KEY_TIMER_STATE";
    private static final String KEY_SCENE_NUMBER =
            "com.acbelter.makesumgame.KEY_SCENE_NUMBER";
    private static final String KEY_GAME_OVER_FLAG =
            "com.acbelter.makesumgame.KEY_GAME_OVER_FLAG";
    private static final int FIELD_SIZE = 4;

    private TextView mPlayerSumView;
    private TextView mFullSumView;
    private Button[][] mFieldButtons;
    private int[][] mFieldNumbers;
    private int mPlayerSum;
    private int mFullSum;
    private TextView mScoreView;
    private TextView mTimerView;
    private long mScore;
    private boolean mFieldClickable;
    private GameScenario mGameScenario;
    private int mSceneNumber;
    private BlinkedCountDownTimer mTimer;
    private TimerState mCurrentTimerState;
    private boolean mGameOverFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game);
        findViews();

        mGameScenario = new SimpleGameScenario();

        if (savedInstanceState != null) {
            initField(Utils.toTwoDimensionArray(savedInstanceState
                    .getIntArray(KEY_FIELD_NUMBERS)));
            mPlayerSum = savedInstanceState.getInt(KEY_PLAYER_SUM);
            mPlayerSumView.setText(String.valueOf(mPlayerSum));
            mFullSum = savedInstanceState.getInt(KEY_FULL_SUM);
            mFullSumView.setText(String.valueOf(mFullSum));
            mScore = savedInstanceState.getLong(KEY_SCORE);
            mScoreView.setText(String.valueOf(mScore));

            ArrayList<Integer> buttonsState =
                    savedInstanceState.getIntegerArrayList(KEY_BUTTONS_STATE);
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    if (buttonsState.contains(mFieldButtons[i][j].getId())) {
                        mFieldButtons[i][j].setSelected(true);
                    }
                }
            }

            mFieldClickable = savedInstanceState.getBoolean(KEY_FIELD_CLICKABLE);
            setFieldClickable(mFieldClickable);

            mCurrentTimerState = savedInstanceState.getParcelable(KEY_TIMER_STATE);
            mSceneNumber = savedInstanceState.getInt(KEY_SCENE_NUMBER);
            mGameOverFlag = savedInstanceState.getBoolean(KEY_GAME_OVER_FLAG);
        } else {
            newGame(mGameScenario.getScene(mSceneNumber).level);
        }

        initFieldButtonsListeners();
    }

    private void initField(Level level) {
        FieldGenerator.setLevel(level);
        mFieldNumbers = FieldGenerator.generateNewField(FIELD_SIZE);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(mFieldNumbers[i][j]));
            }
        }
    }

    private void initField(int[][] fieldNumbers) {
        mFieldNumbers = fieldNumbers;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(mFieldNumbers[i][j]));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGameOverFlag) {
            return;
        }
        if (mCurrentTimerState != null) {
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mCurrentTimerState.millsUntilFinished, 10*1000);
            mTimer.setBlink(mCurrentTimerState.blink);
        } else {
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mGameScenario.getScene(mSceneNumber).timerMillis, 10*1000);
        }
        mTimer.start();
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
                            mPlayerSum += mFieldNumbers[finalI][finalJ];
                        } else {
                            v.setSelected(false);
                            mPlayerSum -= mFieldNumbers[finalI][finalJ];
                            if (mScore < mGameScenario.getScene(mSceneNumber).undoPenalty) {
                                mScore = 0;
                                mScoreView.setText(String.valueOf(mScore));
                                showLoseMessage();
                            } else {
                                mScore -= mGameScenario.getScene(mSceneNumber).undoPenalty;
                                mScoreView.setText(String.valueOf(mScore));
                            }
                        }
                        mPlayerSumView.setText(String.valueOf(mPlayerSum));
                        if (mPlayerSum == mFullSum) {
                            mScore += mGameScenario.getScene(mSceneNumber).madeSumScore;
                            mScoreView.setText(String.valueOf(mScore));
                            if (mSceneNumber == mGameScenario.size()-1) {
                                endGame();
                            } else {
                                showMadeSumMessage();
                                newGame(mGameScenario.getScene(++mSceneNumber).level);
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
        mFieldClickable = false;
        setFieldClickable(false);
        mGameOverFlag = true;
    }

    private void newGame(Level level) {
        Log.d("DEBUG", "Level: " + level.name());
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (mFieldButtons[i][j].isSelected()) {
                    mFieldButtons[i][j].setSelected(false);
                }
            }
        }
        initField(level);
        mPlayerSum = 0;
        mPlayerSumView.setText(String.valueOf(mPlayerSum));
        mFullSum = FieldGenerator.getRandomSum(mFieldNumbers);
        mFullSumView.setText(String.valueOf(mFullSum));
        mFieldClickable = true;

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mGameScenario.getScene(mSceneNumber).timerMillis, 10*1000);
            mTimer.start();
        }
    }

    private void showGameOverMessage() {
        Toast.makeText(this, "Game over!", Toast.LENGTH_LONG).show();
    }

    private void showMadeSumMessage() {
        Toast.makeText(this, "Made sum " + mFullSum + "!", Toast.LENGTH_LONG).show();
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
        outState.putInt(KEY_PLAYER_SUM, mPlayerSum);
        outState.putInt(KEY_FULL_SUM, mFullSum);
        outState.putIntArray(KEY_FIELD_NUMBERS, Utils.toOneDimensionArray(mFieldNumbers));

        ArrayList<Integer> buttonsState = new ArrayList<Integer>();
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (mFieldButtons[i][j].isSelected()) {
                    buttonsState.add(mFieldButtons[i][j].getId());
                }
            }
        }

        outState.putIntegerArrayList(KEY_BUTTONS_STATE, buttonsState);
        outState.putLong(KEY_SCORE, mScore);
        outState.putBoolean(KEY_FIELD_CLICKABLE, mFieldClickable);
        outState.putParcelable(KEY_TIMER_STATE, mTimer.getCurrentState());
    }
}
