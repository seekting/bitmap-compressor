package com.seekting.compressor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.seekting.bitmap.compressor.BitmapCompressor;
import com.seekting.bitmap.compressor.CompressResult;

import java.io.File;
import java.util.List;

/**
 * Created by seekting on 2017/11/28.
 */

public class DemoErrorLaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syncError();

    }

    private void syncError() {
        String path2 = "notexit1.jpg";
        String path3 = "/notexit.jpg";
        String intoDir = "/sdcard/compressor";
        List<CompressResult<String, File>> result = BitmapCompressor
                .add(path2)
                .add(path3)
                .to(intoDir)
                .create()
                .launch();

        for (CompressResult<String, File> objectFileCompressResult : result) {
            if (objectFileCompressResult.getResultError() != null) {
                Log.e("seekting", "error", objectFileCompressResult.getResultError());
            }

        }
    }

}
