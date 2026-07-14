package com.xcyh.reachmax.app.meta.utils.defenceLib.killcompat;

import android.os.Message;

public interface IActivityKiller {

    void finishLaunchActivity(Message message);

    void finishResumeActivity(Message message);

    void finishPauseActivity(Message message);

    void finishStopActivity(Message message);


}
