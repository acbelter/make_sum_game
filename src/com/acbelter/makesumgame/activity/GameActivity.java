package com.acbelter.makesumgame.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.*;
import com.acbelter.makesumgame.GameScenario.GameScene;
import com.acbelter.makesumgame.R.id;
import com.acbelter.makesumgame.R.layout;

import java.util.ArrayList;

public class GameActivity extends BaseGameActivity {
    protected static final String KEY_SCORE =
            "com.acbelter.makesumgame.KEY_SCORE";
    protected static final String KEY_FIELD_CLICKABLE =
            "com.acbelter.makesumgame.KEY_FIELD_CLICKABLE";
    protected static final String KEY_TIMER_STATE =
            "com.acbelter.makesumgame.KEY_TIMER_STATE";
    protected static final String KEY_SCENE_NUMBER =
            "com.acbelter.makesumgame.KEY_SCENE_NUMBER";
    protected TextView mScoreView;
    protected TextView mTimerView;
    protected long mScore;
    protected boolean mFieldClickable;

    private static final int UNDO_PENALTY = 10;
    private static final int MADE_SUM_SCORE = 20;
    private GameScenario mGameScenario;
    private int mSceneNumber;

    private BlinkedCountDownTimer mTimer;
    private TimerState mCurrentTimerState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game);
        findViews();

        mGameScenario = new SimpleGameScenario(10, 20);

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
        } else {
            newGame(mGameScenario.getScene(0));
        }

        initFieldButtonsListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentTimerState != null) {
            mTimer = new BlinkedCountDownTimer(mTimerView,
                    mCurrentTimerState.millsUntilFinished, 11*1000);
            mTimer.setBlink(mCurrentTimerState.blink);
        } else {
            mTimer = new BlinkedCountDownTimer(mTimerView, 31*1000, 11*1000);
        }
        mTimer.start();
    }

    @Override
    protected void findViews() {
        super.findViews();
        mScoreView = (TextView) findViewById(id.score);
        mTimerView = (TextView) findViewById(id.timer);
    }

    @Override
    protected void initFieldButtonsListeners() {
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
                            if (mScore < UNDO_PENALTY) {
                                mScore = 0;
                                mScoreView.setText(String.valueOf(mScore));
                                showLoseMessage();
                            } else {
                                mScore -= UNDO_PENALTY;
                                mScoreView.setText(String.valueOf(mScore));
                            }
                        }
                        mPlayerSumView.setText(String.valueOf(mPlayerSum));
                        if (mPlayerSum == mFullSum) {
                            mScore += MADE_SUM_SCORE;
                            mScoreView.setText(String.valueOf(mScore));
                            showMadeSumMessage();
                            newGame(mGameScenario.getScene(mSceneNumber++));
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void newGame(GameScene scene) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (mFieldButtons[i][j].isSelected()) {
                    mFieldButtons[i][j].setSelected(false);
                }
            }
        }
        initField();
        mPlayerSum = 0;
        mPlayerSumView.setText(String.valueOf(mPlayerSum));
        mFullSum = FieldGenerator.getRandomSum(mFieldNumbers);
        mFullSumView.setText(String.valueOf(mFullSum));
        mFieldClickable = true;
    }

    protected void showLoseMessage() {
        Toast.makeText(this, "You lose!", Toast.LENGTH_LONG).show();
    }

    protected void setFieldClickable(boolean clickable) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setClickable(clickable);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_SCORE, mScore);
        outState.putBoolean(KEY_FIELD_CLICKABLE, mFieldClickable);
        outState.putParcelable(KEY_TIMER_STATE, mTimer.getCurrentState());
    }
}
