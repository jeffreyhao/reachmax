package com.xcyh.reachmax.app.meta.log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.baidu.baselibrary.log.ALog;
import com.base.api.GlobalContext;
import com.xcyh.reachmax.BuildConfig;
import com.xcyh.reachmax.app.meta.utils.DES;

import java.io.File;

/**
 * Time: 2023/12/28
 * Author: lhc
 * Desc:
 */
public class AWSUploadUtil {

    public static String desKey = "xS6aev9h";



    public interface UploadBucketResultListener {

        void onUploadResult(boolean success, File file, int retryIndex, String message);
    }


    /**
     * @param bucket  桶地址，由于 host + 文件目录组成
     * @param remoteFileName 桶上的fileName
     * @param file 本地文件
     */
    public static void uploadFile(File file, String bucket, String remoteFileName, int maxRetryTime, UploadBucketResultListener listener){
        doUpload(file, bucket, remoteFileName,  0,  maxRetryTime, listener);
    }


    private static void doUpload(File file, String bucket, String remoteFileName, int retryIndex, int maxRetryCount, UploadBucketResultListener listener){
        if(file.exists()){
            String accessKey = DES.decryption(BuildConfig.AWS_ACCESS_KEY, desKey);
            String secretKey = DES.decryption(BuildConfig.AWS_SECRET_KEY, desKey);

            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            S3ClientOptions clientOptions = S3ClientOptions.builder().setAccelerateModeEnabled(false).build();
            AmazonS3Client uploadClient = new AmazonS3Client(credentials, Region.getRegion("us-east-1"));
            uploadClient.setS3ClientOptions(clientOptions);

            TransferUtility utility = TransferUtility.builder()
                    .context(GlobalContext.getContext())
                    .s3Client(uploadClient)
                    .build();

            TransferObserver observer = utility.upload(bucket, remoteFileName, file);
            observer.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if(state == TransferState.COMPLETED){
//                        ALog.d("[" + file.getName() + "] ----------上传完成----------");
                        if(listener != null){
                            listener.onUploadResult(true, file, retryIndex, "");
                        }
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

                }

                @Override
                public void onError(int id, Exception e) {
                    ALog.exception("AWSUploadUtil", "doUpload", e);
                    if(retryIndex + 1 < maxRetryCount){
                        doUpload(file, bucket, remoteFileName, retryIndex + 1, maxRetryCount, listener);
                    } else {
                        if(listener!=null){
                            listener.onUploadResult(false, file, retryIndex, e == null ? "onError()" : e.getMessage());
                        }
                    }
                }
            });
        } else {
            if(listener != null){
                listener.onUploadResult(false, file, retryIndex, "file is not exists");
            }
        }
    }





}
