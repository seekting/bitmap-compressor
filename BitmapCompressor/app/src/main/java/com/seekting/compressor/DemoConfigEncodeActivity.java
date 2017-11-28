package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;
import com.seekting.bitmap.compressor.decoder.DecodeConfigKeys;
import com.seekting.bitmap.compressor.encoder.EncoderConfigKeys;

import java.io.File;
import java.util.List;

/**
 * Created by seekting on 2017/11/28.
 */

public class DemoConfigEncodeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        .config(DecodeConfigKeys.MIN_FILE_SIZE, 200 * 1024)//if file size <200*1024 it will decode the whole file.
                        .config(DecodeConfigKeys.MIN_BITMAP_WIDTH, 720)//if bitmap width <=720 it will decode the whole file.else the decode result's width will <720
                        .config(DecodeConfigKeys.MIN_BITMAP_HEIGHT, 720)//if bitmap height <=720 it will decode the whole file.else the decode result's height will <720
                        .to(intoDir)
                        .config(EncoderConfigKeys.COMPRESS_FACTOR, 0.3f)//compress factor,1 means highest quality result.
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
