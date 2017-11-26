package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.EncoderConfigKeys;

import java.io.File;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        String path1 = "/sdcard/DCIM/Camera/moments_1510131377943.jpg";
        String path2 = "/sdcard/DCIM/Camera/moments_1510131393760.jpg";
        String intoDir = "/sdcard/compressor";
        List<File> list = BitmapCompressor
                .add(path1)
                .add(path2)
                .to(intoDir)
                .encodeConfig(EncoderConfigKeys.COMPRESS_FACTOR, 0.6f)
                .create()
                .launch();


    }
}
