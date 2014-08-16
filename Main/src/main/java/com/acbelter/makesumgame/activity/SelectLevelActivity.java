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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.acbelter.makesumgame.LevelsParser;
import com.acbelter.makesumgame.R;
import com.acbelter.makesumgame.Utils;
import com.acbelter.makesumgame.game.Level;

import java.util.ArrayList;
import java.util.List;

public class SelectLevelActivity extends Activity {
    private static final String LEVEL_PREFIX = "level_";
    public static final String KEY_SELECTED_LEVEL =
            "com.acbelter.makesumgame.KEY_SELECTED_LEVEL";
    private static final String KEY_LEVELS =
            "com.acbelter.makesumgame.KEY_LEVELS";
    private static final int RQ_START_GAME = 0;
    private ArrayList<Level> mLevels;
    private GridView mLevelsGrid;
    private LevelsGridAdapter mAdapter;

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        mLevelsGrid = (GridView) findViewById(R.id.levels_grid);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (savedInstanceState != null) {
            mLevels = savedInstanceState.getParcelableArrayList(KEY_LEVELS);
        } else {
            LevelsParser parser = new LevelsParser();
            mLevels = parser.getLevelsFromRes(getResources(), R.xml.levels);
            if (Utils.DEBUG_MODE) {
                for (int i = 0; i < mLevels.size(); i++) {
                    Log.d(Utils.DEBUG_TAG, mLevels.get(i).toString());
                }
            }
        }

        // Open first level
        if (!mPrefs.contains(LEVEL_PREFIX + mLevels.get(0).getId())) {
            mPrefs.edit().putLong(LEVEL_PREFIX + mLevels.get(0).getId(), 0).commit();
        }

        List<LevelItem> levelItems = new ArrayList<LevelItem>(mLevels.size());
        for (int i = 0; i < mLevels.size(); i++) {
            LevelItem newItem = new LevelItem(mLevels.get(i));
            if (mPrefs.contains(LEVEL_PREFIX + mLevels.get(i).getId())) {
                newItem.levelLock = false;
                newItem.maxScore = mPrefs.getLong(LEVEL_PREFIX + mLevels.get(i).getId(), 0);
            }
            levelItems.add(newItem);
        }

        mAdapter = new LevelsGridAdapter(this, levelItems);
        mLevelsGrid.setAdapter(mAdapter);
        mLevelsGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LevelItem selectedItem = mAdapter.getItem(position);
                if (!selectedItem.levelLock) {
                    Level selectedLevel = selectedItem.getLevel();
                    Intent startIntent = new Intent(SelectLevelActivity.this, GameActivity.class);
                    startIntent.putExtra(KEY_SELECTED_LEVEL, selectedLevel);
                    startActivityForResult(startIntent, RQ_START_GAME);
                }
            }
        });
    }

    private int getLevelPos(Level level) {
        for (int i = 0; i < mLevels.size(); i++) {
            if (level.getId() == mLevels.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    public void resetGameProgress(View view) {
        Editor editor = mPrefs.edit();
        for (String key : mPrefs.getAll().keySet()) {
            if (key.startsWith(LEVEL_PREFIX)) {
                editor.remove(key);
            }
        }
        editor.commit();
        Toast.makeText(this, getResources().getString(R.string.reset_progress_msg),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.exit_slide_in, R.anim.exit_slide_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Editor editor = mPrefs.edit();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (!mAdapter.getItem(i).levelLock) {
                int id = mAdapter.getItem(i).getLevel().getId();
                editor.putLong(LEVEL_PREFIX + id, mAdapter.getItem(i).maxScore);
            }
        }
        editor.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LEVELS, mLevels);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RQ_START_GAME && data != null) {
                Level level = data.getParcelableExtra(GameActivity.KEY_LEVEL);
                long score = data.getLongExtra(GameActivity.KEY_SCORE, 0);
                int pos = getLevelPos(level);
                LevelItem levelItem = mAdapter.getItem(pos);
                if (levelItem.maxScore < score) {
                    levelItem.maxScore = score;
                }
                // Unlock next level
                if (score >= level.getCompleteScore() &&
                        pos < mAdapter.getCount()-1 &&
                        mAdapter.getItem(pos+1).levelLock) {
                    mAdapter.getItem(pos+1).levelLock = false;
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
