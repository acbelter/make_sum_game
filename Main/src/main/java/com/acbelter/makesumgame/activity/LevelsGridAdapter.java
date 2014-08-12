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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.acbelter.makesumgame.R;

import java.util.List;

public class LevelsGridAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<LevelItem> mLevelItems;

    public LevelsGridAdapter(Context context, List<LevelItem> levelItems) {
        mLevelItems = levelItems;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView levelNumber;
        ImageView lockImage;
        TextView maxScore;
    }

    @Override
    public int getCount() {
        return mLevelItems.size();
    }

    @Override
    public LevelItem getItem(int position) {
        return mLevelItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mLevelItems.get(position).getLevel().getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_level, null);

            final ViewHolder holder = new ViewHolder();
            holder.levelNumber = (TextView) convertView.findViewById(R.id.level_number);
            holder.lockImage = (ImageView) convertView.findViewById(R.id.lock_image);
            holder.maxScore = (TextView) convertView.findViewById(R.id.max_score);

            convertView.setTag(holder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        LevelItem levelItem = mLevelItems.get(position);
        viewHolder.levelNumber.setText(Integer.toString(levelItem.levelNumber));

        if (!levelItem.levelLock) {
            viewHolder.lockImage.setVisibility(ImageView.GONE);
            viewHolder.maxScore.setVisibility(ImageView.VISIBLE);
            viewHolder.maxScore.setText(Long.toString(levelItem.maxScore));
        } else {
            viewHolder.lockImage.setVisibility(ImageView.VISIBLE);
            viewHolder.maxScore.setVisibility(ImageView.GONE);
        }
        return convertView;
    }
}
