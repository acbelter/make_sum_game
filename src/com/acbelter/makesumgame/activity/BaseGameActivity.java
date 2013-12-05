package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.FieldGenerator;
import com.acbelter.makesumgame.GameScenario.GameScene;
import com.acbelter.makesumgame.R.id;
import com.acbelter.makesumgame.R.layout;
import com.acbelter.makesumgame.Utils;

import java.util.ArrayList;

// TODO Use GameScenario
public class BaseGameActivity extends Activity {
    protected static final String KEY_PLAYER_SUM =
            "com.acbelter.makesumgame.KEY_PLAYER_SUM";
    protected static final String KEY_FULL_SUM =
            "com.acbelter.makesumgame.KEY_FULL_SUM";
    protected static final String KEY_FIELD_NUMBERS =
            "com.acbelter.makesumgame.KEY_FIELD_NUMBERS";
    protected static final String KEY_BUTTONS_STATE =
            "com.acbelter.makesumgame.KEY_BUTTONS_STATE";
    protected static final int FIELD_SIZE = 4;

    protected TextView mPlayerSumView;
    protected TextView mFullSumView;
    protected Button[][] mFieldButtons;

    protected int[][] mFieldNumbers;
    protected int mPlayerSum;
    protected int mFullSum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_training_game);
        findViews();

        if (savedInstanceState != null) {
            initField(Utils.toTwoDimensionArray(savedInstanceState
                    .getIntArray(KEY_FIELD_NUMBERS)));
            mPlayerSum = savedInstanceState.getInt(KEY_PLAYER_SUM);
            mPlayerSumView.setText(String.valueOf(mPlayerSum));
            mFullSum = savedInstanceState.getInt(KEY_FULL_SUM);
            mFullSumView.setText(String.valueOf(mFullSum));

            ArrayList<Integer> buttonsState =
                    savedInstanceState.getIntegerArrayList(KEY_BUTTONS_STATE);
            for (int i = 0; i < FIELD_SIZE; i++) {
                for (int j = 0; j < FIELD_SIZE; j++) {
                    if (buttonsState.contains(mFieldButtons[i][j].getId())) {
                        mFieldButtons[i][j].setSelected(true);
                    }
                }
            }
        } else {
            newGame(null);
        }

        initFieldButtonsListeners();
    }

    protected void findViews() {
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
    }

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
                        }
                        mPlayerSumView.setText(String.valueOf(mPlayerSum));
                        if (mPlayerSum == mFullSum) {
                            showMadeSumMessage();
                            newGame(null);
                        }
                    }
                });
            }
        }
    }

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
    }

    protected void showMadeSumMessage() {
        Toast.makeText(this, "Made sum " + mFullSum + "!", Toast.LENGTH_LONG).show();
    }

    protected void initField() {
        mFieldNumbers = FieldGenerator.generateNewField(FIELD_SIZE);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(mFieldNumbers[i][j]));
            }
        }
    }

    protected void initField(int[][] fieldNumbers) {
        mFieldNumbers = fieldNumbers;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(mFieldNumbers[i][j]));
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
    }
}
