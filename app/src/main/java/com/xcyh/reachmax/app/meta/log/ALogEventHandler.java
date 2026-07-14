package com.xcyh.reachmax.app.meta.log;

import com.baidu.baselibrary.log.process.ALogEvent;
import com.baidu.baselibrary.util.sys.EventUtil;
import com.base.watcher.IWatched;
import com.base.watcher.Watcher;
import com.base.watcher.WatcherEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by haojiangfeng on 2024/6/19.
 */
public class ALogEventHandler implements IWatched {

    public void init(){
        EventUtil.register(this);
        Watcher.getInstance().registerDataSetObserver(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onALogEvent(ALogEvent event) {
        if(event == null){
            return;
        }
        switch (event.type){
            case ALogEvent.TYPE_OUT_OF_DATE:
                if(event.object instanceof List){
                    List<String> fileNames = (List<String>) event.object;
                    AlogUploader.setOutOfDateFiles(fileNames);
                    AlogUploader.postUploadAlogFiles();
                }
                break;
        }
    }


    @Override
    public void notifyWatcher(int event, Object object) {
        switch (event){
            case WatcherEvent.EVENT_APP_SWITCH_BACKSTAGE: // 切后台
            case WatcherEvent.EVENT_APP_EXIT_APP:         // 退出app
                AlogUploader.postUploadAlogFiles();
                break;
        }
    }
}
