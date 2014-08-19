package com.acbelter.makesumgame;

import android.content.res.Resources;
import android.util.Log;
import com.acbelter.makesumgame.game.Level;
import com.acbelter.makesumgame.game.Scene;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class LevelsParser {
    private Level mLevel;
    private Scene mScene;
    private String mName;
    private String mValue;

    public ArrayList<Level> getLevelsFromRes(Resources res, int levelsXmlId) {
        ArrayList<Level> levels = new ArrayList<Level>();
        XmlPullParser xpp = res.getXml(levelsXmlId);
        try {
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (xpp.getEventType()) {
                    case XmlPullParser.START_TAG: {
                        if ("level".equals(xpp.getName())) {
                            mLevel = new Level();
                            for (int i = 0; i < xpp.getAttributeCount(); i++) {
                                mName = xpp.getAttributeName(i);
                                mValue = xpp.getAttributeValue(i);
                                if ("id".equals(mName)) {
                                    mLevel.setId(Integer.valueOf(mValue));
                                } else if ("completeScore".equals(mName)) {
                                    mLevel.setCompleteScore(Long.valueOf(mValue));
                                }
                            }
                        } else if ("scene".equals(xpp.getName())) {
                            mScene = new Scene();
                            for (int i = 0; i < xpp.getAttributeCount(); i++) {
                                mName = xpp.getAttributeName(i);
                                mValue = xpp.getAttributeValue(i);
                                if ("difficulty".equals(mName)) {
                                    mScene.setDifficulty(mValue);
                                } else if ("timerSeconds".equals(mName)) {
                                    mScene.setTimerSeconds(Integer.valueOf(mValue));
                                } else if ("undoPenalty".equals(mName)) {
                                    mScene.setUndoPenalty(Long.valueOf(mValue));
                                } else if ("madeSumScore".equals(mName)) {
                                    mScene.setMadeSumScore(Long.valueOf(mValue));
                                }
                            }
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        if ("level".equals(xpp.getName())) {
                            levels.add(mLevel);
                        } else if ("scene".equals(xpp.getName())) {
                            mLevel.addScene(mScene);
                        }
                        break;
                    }
                }
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(Utils.DEBUG_TAG, "Can't parse levels from resources: XmlPullParserException");
        } catch (IOException e) {
            Log.e(Utils.DEBUG_TAG, "Can't parse levels from resources: IOException");
        }
        return levels;
    }
}
