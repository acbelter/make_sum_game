package com.acbelter.makesumgame.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.R.id;
import com.acbelter.makesumgame.R.layout;
import com.acbelter.makesumgame.Utils;

import java.util.ArrayList;

public class GameActivity extends BaseGameActivity {
    protected static final String KEY_SCORE =
            "com.acbelter.makesumgame.KEY_SCORE";
    protected static final String KEY_FIELD_CLICKABLE =
            "com.acbelter.makesumgame.KEY_FIELD_CLICKABLE";
    protected GridLayout mField;
    protected TextView mScoreView;
    protected long mScore;

    private static final int UNDO_PENALTY = 10;
    private static final int MADE_SUM_SCORE = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game);
        findViews();

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

            mField.setClickable(savedInstanceState.getBoolean(KEY_FIELD_CLICKABLE));
        } else {
            newGame();
        }

        initFieldButtonsListeners();
    }

    @Override
    protected void findViews() {
        super.findViews();
        mField = (GridLayout) findViewById(id.field);
        mScoreView = (TextView) findViewById(id.score);
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
                                mField.setClickable(false);
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
                            newGame();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void newGame() {
        // FIXME FieldGenerator.getRandomSum() called two times
        super.newGame();
    }

    protected void showLoseMessage() {
        Toast.makeText(this, "You lose!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_SCORE, mScore);
        outState.putBoolean(KEY_FIELD_CLICKABLE, mField.isClickable());
    }
}
