package com.xcyh.reachmax.app.meta.log;

import android.text.TextUtils;

import com.baidu.baselibrary.log.ALog;
import com.baidu.baselibrary.log.annotate.LogFilePrefix;
import com.baidu.baselibrary.util.App;
import com.baidu.baselibrary.util.date.DateUtil;
import com.base.util.sys.FileUtil;
import com.base.global.PreferencesUtil;
import com.base.util.AppUtil;
import com.xcyh.reachmax.app.meta.novelverse.utils.Constant;
import com.xcyh.reachmax.app.meta.utils.PPFileUtils;
import com.xcyh.reachmax.model.manager.Pitcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haojiangfeng on 2024/4/28.
 */
public class AlogUploader {


    static Runnable sUploadAlogRunnable = null;
    static List<String> sOutOfDateLogNames = new ArrayList<>();

    /**
     * https://app-screenshot.s3.amazonaws.com/alog/2024-01-04/1810228.txt
     *
     * 上报 Alog 日志文件
     * <br>
     * 调用时机：
     *      1.启动app后（第10秒上报，发现有过期日志上报）
     *      2.阅读页onStop
     *      3.支付页onDestroy
     *      4.app切后台
     *      5.点击反馈页
     *      6.200秒轮循发现有异常退出阅读页
     */
    public static void postUploadAlogFiles(){
        postUploadAlogFiles(200);
    }

    public static void postUploadAlogFiles(long delayMills){
        if(sUploadAlogRunnable == null){
            sUploadAlogRunnable = new Runnable() {
                @Override
                public void run() {
                    doUploadAlog();
                }
            };
        }
        App.getHandler().removeCallbacks(sUploadAlogRunnable);
        App.getHandler().postDelayed(sUploadAlogRunnable, delayMills);
    }


    /**
     * 立刻上报
     */
    public static void uploadAlogRightNow(){
        if(sUploadAlogRunnable != null){
            App.getHandler().removeCallbacks(sUploadAlogRunnable);
            sUploadAlogRunnable = null;
        }
        doUploadAlog();
    }

    private static void doUploadAlog(){
        PreferencesUtil.put(Constant.SP_CONSTANT.NEED_UPLOAD_ALOG, true);
        gzipAndUpload();
    }





    private static void gzipAndUpload(){
        try {
            String logDir = ALog.getLogDir();
            File logDirFile = new File(logDir);
            if(!logDirFile.exists()) {
                PreferencesUtil.put(Constant.SP_CONSTANT.NEED_UPLOAD_ALOG, false);
                ALog.textSingle("Alog upload return : log files is null.");
                return;
            }
            File[] sourceFiles = logDirFile.listFiles();
            if(sourceFiles == null || sourceFiles.length == 0){
                PreferencesUtil.put(Constant.SP_CONSTANT.NEED_UPLOAD_ALOG, false);
                ALog.textSingle("Alog upload return : log files is empty.");
                return;
            }

            String uploadDir = logDir.replace("log", "logUpload");
            FileUtil.createDirAndClear(uploadDir);

            // gzip压缩，并上传
            for(File sourceFile : sourceFiles){ // /storage/emulated/0/Android/data/com.benefit.novelverse/cache/log/util-2024-01-15.txt
                String zipFilePath = uploadDir + getZipFileName(sourceFile);
                PPFileUtils.gzipFolder(sourceFile, zipFilePath);

                File zipFile = new File(zipFilePath);
                uploadFile(zipFile);
            }

        } catch (Throwable e){
            ALog.exception("AlogUploader", "gzipAndUpload", e);
            PreferencesUtil.put(Constant.SP_CONSTANT.NEED_UPLOAD_ALOG, false);
        }
    }

    /**
     * 获取压缩文件命名
     */
    private static String getZipFileName(File sourceFile){
        if(sourceFile == null){
            return "";
        }
        // alog-2024-01-15.txt
        String sourceFileNameDate = sourceFile.getName();
        // alog-2024-01-15.zip
        return sourceFileNameDate.substring(0, sourceFileNameDate.lastIndexOf('.')) + ".zip";
    }

    private static void uploadFile(File file){
        if(!file.exists()){
            return;
        }
        // 本地：alog-2024-01-15.zip
        // 远程：/app-screenshot/alog/2024-04-28/10242057_alog-2024-01-15.zip
        // 远程文件名，使用uid，如没有uid则用时间戳
        String localFileName = file.getName(); // alog-2024-01-15.zip
        String remoteDir = buildRemoteDir(localFileName);
        String remoteFileName = buildRemoteName(localFileName);
        ALog.textSingle("Upload alog : " + remoteDir + "/" + remoteFileName);

        AWSUploadUtil.uploadFile(file, remoteDir, remoteFileName, 3, new AWSUploadUtil.UploadBucketResultListener() {
            @Override
            public void onUploadResult(boolean success, File file, int retryIndex, String message) {
                long fileLength = file.length();
                boolean deleteSuccess = file.delete();
                if(success){
                    ALog.textSingle("Alog upload success. ZipFile [" + file.getName() + ", " + fileLength + "B] " + (deleteSuccess ? "delete success." : "delete fail."));
                    PreferencesUtil.put(Constant.SP_CONSTANT.NEED_UPLOAD_ALOG, false);
                    processUploadSuccess(file, remoteFileName);
                } else {
                    ALog.textSingle("Alog upload fail. ZipFile [" + file.getName() + ", " + fileLength + "B] " + (deleteSuccess ? "delete success." : "delete fail."));
                }
            }
        });
    }

    private static String getFileDate(String localFileName){
        // alog-2024-01-15.zip -> 2024-01-15.zip -> 2024-01-15
        return localFileName.substring(localFileName.indexOf("-") + 1).replace(".zip", "");
    }

    private static String getFilePrefix(String localFileName){
        // alog-2024-01-15.zip -> alog
        return localFileName.substring(0, localFileName.indexOf("-"));
    }


    private static String buildRemoteDir(String localFileName){
        String date = getFileDate(localFileName); // 2024-01-15
        String year = date.substring(0, 4); // 2024
        String monthDay = date.substring(5); // 01-15

        if(localFileName.contains(LogFilePrefix.CSH)){
            return "xcyh-app-front-log/reachmax-android/" + year + "/" + monthDay + "/crash";
        } else {
            String userName = Pitcher.getInstance().getUserName();
            if(TextUtils.isEmpty(userName)){
                userName = "000000";
            }
            return "xcyh-app-front-log/reachmax-android/" + year + "/" + monthDay + "/" + userName;
        }
    }

    /**
     * @return 远程文件名，使用uid，如没有uid则用时间戳
     */
    private static String buildRemoteName(String localFileName){
        if(TextUtils.isEmpty(Pitcher.getInstance().getUserName())){
            String filePrefix = getFilePrefix(localFileName);
            return DateUtil.getFixedTimeStamp() + "-" + filePrefix + ".zip";
        }
        String time = DateUtil.getFixedMdHmsWitBeijingTime();  // 北京时区时间

        if(localFileName.contains(LogFilePrefix.CSH)) {
            String packageName = AppUtil.getPackageName();
            String versionName = AppUtil.getVersionName();

            // 2062055_alphafiction_v1.3.0_crash-2024-08-17.zip
            return Pitcher.getInstance().getUserName() + "_" + packageName + "_v" + versionName + "_" + time + "_" + localFileName;

        } else {
            // 远程文件命名： {uploadTime_localFileName}.zip
            // 0626223505_alog-2024-01-15.zip
            return time + "_" + localFileName;
        }
    }



    /**
     * 上报成功后的处理
     */
    private static void processUploadSuccess(File zipFile, String remoteFileName){
        // 本地日志源文件   /sdcard/Android/data/com.star.novelhive/cache/log/standard-2024-08-19.txt
        // 本地日志zip文件  /sdcard/Android/data/com.star.novelhive/cache/logUpload/standard-2024-06-27.zip
        // 远程日志命名     2024-08-17/1767779_0817000145_standard-2024-08-17.zip
        // 以前的远程日志命名  1855826_alog-2024-08-15.zip
        // 以前的远程日志命名  2062055_195211_crash-2024-08-17.zip

        String zipFileName = zipFile.getName().replace(".zip", "");

        // 删除crash类型的文件
        if(needDeleteOriginFile(zipFileName)){
            File logDir = new File(ALog.getLogDir());
            if(logDir.exists()) {
                File[] logFiles = logDir.listFiles();
                if(logFiles != null && logFiles.length > 0){
                    for(File logFile : logFiles){
                        // csh-2024-01-15
                        String logFileName = logFile.getName();
                        String localFileName = logFile.getName().replace(".txt", "");
                        if(zipFileName.contains(localFileName)){
                            FileUtil.delete(logFile);
                            sOutOfDateLogNames.remove(zipFileName);
                        }
                    }
                }
            }
        } else {
//            ALog.textSingle("AlogUploader", "processUploadSuccess", "Alog need not delete. ZipFile [" + file.getName() + ", " + fileLength + "B] " + (deleteSuccess ? "delete success." : "delete fail."));
        }
    }

    private static boolean needDeleteOriginFile(String zipFileName) {
        if(zipFileName.contains(LogFilePrefix.CSH)){
            return true;
        }
        if(sOutOfDateLogNames.contains(zipFileName)){
            return true;
        }
        return false;
    }


    public static void setOutOfDateFiles(List<String> fileNames) {
        sOutOfDateLogNames.clear();
        if(fileNames != null && fileNames.size() > 0){
            sOutOfDateLogNames.addAll(fileNames);
        }
    }
}
