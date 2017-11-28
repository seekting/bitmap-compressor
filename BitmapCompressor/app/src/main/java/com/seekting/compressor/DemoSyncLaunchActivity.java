package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;
import com.seekting.demo_lib.Demo;

import java.io.File;
import java.util.List;

@Demo(title = "launch task by sync", desc = "launch task in current thread,result will sync return.")
public class DemoSyncLaunchActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sync();


    }


    private void sync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                String path3 = "/sdcard/DCIM/Camera/moments_15101313937601.jpg";
                String intoDir = "/sdcard/compressor";
                List<CompressResult<String, File>> list = BitmapCompressor
                        .add(path1)
                        .add(path2)
                        .add(path3)
                        .to(intoDir)
                        .debug()
                        .create()
                        .launch();
//


                for (CompressResult<String, File> result : list) {
                    if (result.getResultError() != null) {
                        Log.e("seekting", result.toString());
                    } else {
                        Log.d("seekting", result.toString());

                    }
                }

            }
        }).start();
    }
}
