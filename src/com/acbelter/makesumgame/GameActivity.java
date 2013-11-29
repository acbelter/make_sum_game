package com.acbelter.makesumgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.R.id;
import com.acbelter.makesumgame.R.layout;

public class GameActivity extends Activity {
    private static final String KEY_PLAYER_SUM =
            "com.acbelter.makesumgame.KEY_PLAYER_SUM";
    private static final String KEY_FULL_SUM =
            "com.acbelter.makesumgame.KEY_FULL_SUM";
    private static final String KEY_TIMER =
            "com.acbelter.makesumgame.KEY_TIMER";
    private static final String KEY_FIELD_NUMBERS =
            "com.acbelter.makesumgame.KEY_FIELD_NUMBERS";
    private static final int FIELD_SIZE = 4;

    private TextView mPlayerSumView;
    private TextView mFullSumView;
    private TextView mTimerView;

    private Button[][] mFieldButtons;
    private int[][] mFieldNumbers;
    private int mPlayerSum;
    private int mFullSum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game);

        mPlayerSumView = (TextView) findViewById(id.player_sum);
        mFullSumView = (TextView) findViewById(id.full_sum);
        mTimerView = (TextView) findViewById(id.timer);

        mFieldButtons = new Button[4][4];
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

        if (savedInstanceState != null) {
            initField(Utils.toTwoDimensionArray(savedInstanceState
                    .getIntArray(KEY_FIELD_NUMBERS)));
            mPlayerSumView.setText(savedInstanceState.getCharSequence(KEY_PLAYER_SUM));
            mPlayerSum = Integer.parseInt(mPlayerSumView.getText().toString());
            mFullSumView.setText(savedInstanceState.getCharSequence(KEY_FULL_SUM));
            mFullSum = Integer.parseInt(mFullSumView.getText().toString());
            mTimerView.setText(savedInstanceState.getCharSequence(KEY_TIMER));
        } else {
            newGame();
        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                final int finalI = i;
                final int finalJ = j;
                mFieldButtons[i][j].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.isSelected()) {
                            v.setSelected(false);
                            mPlayerSum -= mFieldNumbers[finalI][finalJ];
                            mPlayerSumView.setText(String.valueOf(mPlayerSum));
                        } else {
                            v.setSelected(true);
                            mPlayerSum += mFieldNumbers[finalI][finalJ];
                            mPlayerSumView.setText(String.valueOf(mPlayerSum));
                            if (mPlayerSum == mFullSum) {
                                showWinMessage();
                                newGame();
                            }
                        }
                    }
                });
            }
        }
    }

    private void newGame() {
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
        mTimerView.setText("00:30");
    }

    private void showWinMessage() {
        Toast.makeText(this, "Made sum " + mFullSum, Toast.LENGTH_LONG).show();
    }

    private void initField() {
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
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Save buttons state
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_PLAYER_SUM, mPlayerSumView.getText());
        outState.putCharSequence(KEY_FULL_SUM, mFullSumView.getText());
        outState.putCharSequence(KEY_TIMER, mTimerView.getText());
        outState.putIntArray(KEY_FIELD_NUMBERS, Utils.toOneDimensionArray(mFieldNumbers));
    }
}
