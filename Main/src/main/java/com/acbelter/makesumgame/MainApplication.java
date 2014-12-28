package com.acbelter.makesumgame;

import android.app.Application;
import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "", // will not be used
        mailTo = "acbelter.app@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.toast_crash)
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
