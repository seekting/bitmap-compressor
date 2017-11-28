package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CancelAble;
import com.seekting.bitmap.compressor.CompressCallBack;
import com.seekting.bitmap.compressor.CompressResult;
import com.seekting.demo_lib.Demo;

import java.io.File;
import java.util.List;

/**
 * Created by seekting on 2017/11/28.
 */
@Demo(title = "launch task width callback", desc = "you can launch by callback way,and you can get a cancelAble to cancel the launched task")
public class DemoLaunchWithCallBack extends Activity {
    private CompressCallBack<String, File> mCallBack;
    private CancelAble mCancelAble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncCallBack();

    }

    private void asyncCallBack() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                mCallBack = new CompressCallBack<String, File>() {
                    @Override
                    public void onCompressEnd(List<CompressResult<String, File>> compressResults) {
                        for (CompressResult<String, File> result : compressResults) {
                            if (result.getResultError() != null) {
                                Log.e("seekting", result.toString());
                            } else {
                                Log.d("seekting", result.toString());

                            }
                        }

                    }
                };
                mCancelAble = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .create()
                        .launchAsync(mCallBack);


            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCancelAble != null) {
            mCancelAble.cancel();
        }
        mCallBack = null;

    }
}
