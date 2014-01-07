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

    public Level(int id, long completeScore) {
        mId = id;
        mCompleteScore = completeScore;
        mLevelScenes = new ArrayList<Scene>();
    }

    private Level(Parcel in) {
        mId = in.readInt();
        mCompleteScore = in.readLong();
        Scene[] scenes = (Scene[]) in.readParcelableArray(Level.class.getClassLoader());
        mLevelScenes = Arrays.asList(scenes);
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
}
