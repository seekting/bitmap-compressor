package com.seekting.compressor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class DemoCustomCompress1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
                String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
                BitmapCompressor.registerEncoder(Bitmap.class, new BitmapEncoder());
                List<String> sources = new ArrayList<>();
                sources.add(path1);
                sources.add(path2);

                List<CompressResult<String, Bitmap>> list = BitmapCompressor.addAll(sources).debug().to(Bitmap.class).create().launch();
                for (CompressResult<String, Bitmap> compressResult : list) {
                    Log.d("seekting", "compressResult=" + compressResult);
                    if (compressResult.getResultError() != null) {
                        compressResult.getResultError().printStackTrace();
                    }
                }

            }
        }).start();

    }
}
