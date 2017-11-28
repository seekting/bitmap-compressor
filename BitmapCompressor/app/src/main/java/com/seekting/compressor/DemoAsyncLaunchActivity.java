package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;
import com.seekting.bitmap.compressor.FutureTarget;
import com.seekting.demo_lib.Demo;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by seekting on 2017/11/28.
 */

@Demo(title = "async launch", desc = "you can launch task in background thread,and use FutureTarget to control compress task")
public class DemoAsyncLaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        async();

    }

    private void async() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                FutureTarget<String, File> futureTarget = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .create()
                        .launchAsync();
                try {
                    List<CompressResult<String, File>> list = futureTarget.get();//block
                    for (CompressResult<String, File> result : list) {
                        if (result.getResultError() != null) {
                            Log.e("seekting", result.toString());
                        } else {
                            Log.d("seekting", result.toString());

                        }
                    }
                } catch (Throwable e) {
                    Log.e("seekting", "error", e);
                }


            }
        }).start();
    }

    private void async(final long time, final TimeUnit timeUnit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                FutureTarget<String, File> futureTarget = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .create()
                        .launchAsync();
                try {
                    List<CompressResult<String, File>> list = futureTarget.get(time, timeUnit);//block
                    for (CompressResult<String, File> result : list) {
                        if (result.getResultError() != null) {
                            Log.e("seekting", result.toString());
                        } else {
                            Log.d("seekting", result.toString());

                        }
                    }
                } catch (Throwable e) {
                    Log.e("seekting", "error", e);
                }


            }
        }).start();
    }
}
