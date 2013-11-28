package com.acbelter.makesumgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
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

    private TextView mPlayerSum;
    private TextView mFullSum;
    private TextView mTimer;

    private Button[][] mFieldButtons;
    private int[][] mFieldNumbers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game);

        mPlayerSum = (TextView) findViewById(id.player_sum);
        mFullSum = (TextView) findViewById(id.full_sum);
        mTimer = (TextView) findViewById(id.timer);

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
            mPlayerSum.setText(savedInstanceState.getCharSequence(KEY_PLAYER_SUM));
            mFullSum.setText(savedInstanceState.getCharSequence(KEY_FULL_SUM));
            mTimer.setText(savedInstanceState.getCharSequence(KEY_TIMER));
            initField(toTwoDimensionArray(savedInstanceState.getIntArray(KEY_FIELD_NUMBERS)));
        } else {
            mPlayerSum.setText("21");
            mFullSum.setText("64");
            mTimer.setText("00:25");
            initField();
        }
    }

    private void initField() {
        mFieldNumbers = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                mFieldButtons[i][j].setText(String.valueOf(0));
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

    private static int[] toOneDimensionArray(int[][] array) {
        int[] result = new int[FIELD_SIZE * FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                result[i * FIELD_SIZE + j] = array[i][j];
            }
        }
        return result;
    }

    private static int[][] toTwoDimensionArray(int[] array) {
        int result[][] = new int[FIELD_SIZE][FIELD_SIZE];
        int counter = 0;
        for (int i = 0; i < FIELD_SIZE * FIELD_SIZE; i++) {
            result[i % FIELD_SIZE][counter] = array[i];
            if (counter == FIELD_SIZE - 1) {
                counter = 0;
            } else {
                counter++;
            }
        }
        return result;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_PLAYER_SUM, mPlayerSum.getText());
        outState.putCharSequence(KEY_FULL_SUM, mFullSum.getText());
        outState.putCharSequence(KEY_TIMER, mTimer.getText());
        outState.putIntArray(KEY_FIELD_NUMBERS, toOneDimensionArray(mFieldNumbers));
    }
}
