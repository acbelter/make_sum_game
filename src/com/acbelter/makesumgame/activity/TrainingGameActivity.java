package com.acbelter.makesumgame.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.acbelter.makesumgame.R.id;
import com.acbelter.makesumgame.R.layout;
import com.acbelter.makesumgame.game.BaseGameState;
import com.acbelter.makesumgame.game.FieldGenerator;
import com.acbelter.makesumgame.game.FieldGenerator.Level;
import com.acbelter.makesumgame.game.TrainingGameState;

public class TrainingGameActivity extends Activity {
    private static final int FIELD_SIZE = 4;
    private TrainingGameState mGameState;
    private Button[][] mFieldButtons;
    private TextView mPlayerSumView;
    private TextView mFullSumView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_training_game);
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

    private Level loadLevelFromPref() {
        // TODO Load Level from Pref
        return Level.EASY;
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

    private void newGame(Level level) {
        int[][] field = newField(level);
        int fullSum = FieldGenerator.getRandomSum(field);
        mGameState = new TrainingGameState(field, 0, fullSum);

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
                Toast.LENGTH_LONG).show();
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
