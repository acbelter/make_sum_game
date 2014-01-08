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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.acbelter.makesumgame.Test;
import com.acbelter.makesumgame.game.Level;

import java.util.ArrayList;
import java.util.List;

public class SelectLevelActivity extends ListActivity {
    public static final String KEY_SELECTED_LEVEL =
            "com.acbelter.makesumgame.KEY_SELECTED_LEVEL";
    private static final String KEY_LEVELS =
            "com.acbelter.makesumgame.KEY_LEVELS";
    private static final int RQ_START_GAME = 0;
    private ArrayList<Level> mLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLevels = savedInstanceState.getParcelableArrayList(KEY_LEVELS);
        } else {
            mLevels = Test.generateTestLevels();
        }

        List<LevelItem> levelItems = new ArrayList<LevelItem>(mLevels.size());
        for (int i = 0; i < mLevels.size(); i++) {
            levelItems.add(new LevelItem(mLevels.get(i)));
        }

        final LevelsListAdapter adapter = new LevelsListAdapter(this, levelItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Level selectedLevel = adapter.getItem(position).getLevel();
                Intent startIntent = new Intent(SelectLevelActivity.this, GameActivity.class);
                startIntent.putExtra(KEY_SELECTED_LEVEL, selectedLevel);
                startActivityForResult(startIntent, RQ_START_GAME);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LEVELS, mLevels);
    }
}
