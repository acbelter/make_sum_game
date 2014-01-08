package com.acbelter.makesumgame.game;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level implements Parcelable {
    private int mId;
    private long mCompleteScore;
    private List<Scene> mLevelScenes;

    public Level() {
        mLevelScenes = new ArrayList<Scene>();
    }

    public Level(int id, long completeScore) {
        mId = id;
        mCompleteScore = completeScore;
        mLevelScenes = new ArrayList<Scene>();
    }

    private Level(Parcel in) {
        mId = in.readInt();
        mCompleteScore = in.readLong();
        Parcelable[] array = in.readParcelableArray(Scene.class.getClassLoader());
        Scene[] scenes = Arrays.copyOf(array, array.length, Scene[].class);
        mLevelScenes = Arrays.asList(scenes);
    }

    public Scene getSceneWithIndex(int index) {
        if (index < 0 || index > mLevelScenes.size()-1) {
            return null;
        }
        return mLevelScenes.get(index);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public long getCompleteScore() {
        return mCompleteScore;
    }

    public void setCompleteScore(long completeScore) {
        mCompleteScore = completeScore;
    }

    public List<Scene> getLevelScenes() {
        return mLevelScenes;
    }

    public void setLevelScenes(List<Scene> levelScenes) {
        mLevelScenes = levelScenes;
    }

    public void addScene(Scene scene) {
        mLevelScenes.add(scene);
    }

    public static final Parcelable.Creator<Level> CREATOR =
            new Parcelable.Creator<Level>() {

                @Override
                public Level createFromParcel(Parcel source) {
                    return new Level(source);
                }

                @Override
                public Level[] newArray(int size) {
                    return new Level[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeLong(mCompleteScore);
        Scene[] array = new Scene[mLevelScenes.size()];
        out.writeParcelableArray(mLevelScenes.toArray(array), flags);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("level_id: ");
        builder.append(mId);
        builder.append("; ");
        builder.append("level_size: ");
        builder.append(mLevelScenes.size());
        builder.append("\n");
        for (int i = 0; i < mLevelScenes.size(); i++) {
            builder.append("\t");
            builder.append(mLevelScenes.get(i).toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}
