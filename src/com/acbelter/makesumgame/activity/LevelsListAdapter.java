package com.acbelter.makesumgame.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.acbelter.makesumgame.R;

import java.util.List;

public class LevelsListAdapter extends ArrayAdapter<LevelItem> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<LevelItem> mLevelItems;

    public LevelsListAdapter(Context context, List<LevelItem> levelItems) {
        super(context, R.layout.item_level, levelItems);
        mContext = context;
        mLevelItems = levelItems;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        TextView levelLabel;
        TextView maxScore;
        ImageView lockImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_level, null);

            final ViewHolder holder = new ViewHolder();
            holder.levelLabel = (TextView) convertView.findViewById(R.id.level_label);
            holder.maxScore = (TextView) convertView.findViewById(R.id.max_score);
            holder.lockImage = (ImageView) convertView.findViewById(R.id.lock_image);
            convertView.setTag(holder);
        }

        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        LevelItem levelItem = mLevelItems.get(position);
        viewHolder.levelLabel.setText(mContext.getResources().getString(R.string.level) +
                " " + levelItem.levelNumber);
        viewHolder.maxScore.setText(Long.toString(levelItem.maxScore));
        if (!levelItem.levelLock) {
            viewHolder.lockImage.setVisibility(ImageView.INVISIBLE);
        }
        return convertView;
    }
}
